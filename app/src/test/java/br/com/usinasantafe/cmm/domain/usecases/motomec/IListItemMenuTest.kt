package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.FERTIGATION
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.IMPLEMENT
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.MECHANICAL
import br.com.usinasantafe.cmm.lib.PCOMP_INPUT
import br.com.usinasantafe.cmm.lib.PERFORMANCE
import br.com.usinasantafe.cmm.lib.PMM
import br.com.usinasantafe.cmm.lib.REEL
import br.com.usinasantafe.cmm.lib.TIRE
import br.com.usinasantafe.cmm.lib.TRANSHIPMENT
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.lib.WORK
import br.com.usinasantafe.cmm.lib.appList
import br.com.usinasantafe.cmm.lib.typeListPMM
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IListItemMenuTest {

    private val itemMenuRepository = mock<ItemMenuRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val functionActivityRepository = mock<FunctionActivityRepository>()
    private val functionStopRepository = mock<FunctionStopRepository>()
    private val usecase = IListItemMenu(
        itemMenuRepository = itemMenuRepository,
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository,
        functionActivityRepository = functionActivityRepository,
        functionStopRepository = functionStopRepository
    )

    @Test
    fun `Check return failure if not exist flavor fielded`() =
        runTest {
            wheneverRegister(1)
            val result = usecase("test")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: Flavor not found"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdEquipHeader - PMM`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdEquipHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdEquipHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen - PMM`() =
        runTest {
            wheneverRegister(1)
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getTypeEquipByIdEquip - PMM`() =
        runTest {
            wheneverRegister(2)
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getTypeEquipByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IEquipRepository.getTypeEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdActivityHeader - PMM`() =
        runTest {
            wheneverRegister(3)
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdActivityHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdActivityHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityRepository listByIdActivity - PMM`() =
        runTest {
            wheneverRegister(4)
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRepository.listByIdActivity",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IFunctionActivityRepository.listByIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getFlagMechanicByIdEquip - PMM`() =
        runTest {
            wheneverRegister(5)
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getFlagMechanicByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IEquipRepository.getFlagMechanicByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getFlagTireByIdEquip - PMM`() =
        runTest {
            wheneverRegister(6)
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getFlagTireByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IEquipRepository.getFlagTireByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionStopRepository getIdStopByType - PMM`() =
        runTest {
            wheneverRegister(7)
            whenever(
                functionStopRepository.getIdStopByType(TypeStop.REEL)
            ).thenReturn(
                resultFailure(
                    "IFunctionStopRepository.getIdStopByType",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IFunctionStopRepository.getIdStopByType"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository checkNoteHasByIdStop - PMM`() =
        runTest {
            wheneverRegister(8)
            whenever(
                motoMecRepository.hasNoteByIdStopAndIdHeader(1, 1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.checkNoteHasByIdStop",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.checkNoteHasByIdStop"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemMenuPMMRepository listByTypeList - PMM`() =
        runTest {
            wheneverRegister(9)

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(1 to ITEM_NORMAL)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list
                )
            ).thenReturn(
                resultFailure(
                    "IItemMenuPMMRepository.listByTypeList",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IListItemMenu.pmmList -> IItemMenuPMMRepository.listByTypeList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully, flow basic and emptyList - PMM`() =
        runTest {

            wheneverRegister(9)

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(1 to ITEM_NORMAL)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list
                )
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun `Check return correct if function execute successfully, flow basic and with list - PMM`() =
        runTest {

            wheneverRegister(9)

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(1 to ITEM_NORMAL)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenu(
                            id = 1,
                            descr = "ITEM 1",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 1 to PMM,
                        ),
                        ItemMenu(
                            id = 2,
                            descr = "ITEM 2",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 1 to PMM,
                        )
                    )
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            val itemList = result.getOrNull()!!
            assertEquals(
                itemList.size,
                2
            )
            val item1 = itemList[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.descr,
                "ITEM 1"
            )
            val item2 = itemList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.descr,
                "ITEM 2"
            )
        }

    @Test
    fun `Check return correct if function execute successfully, type equip is NORMAL, all item and with list - PMM`() =
        runTest {

            wheneverRegister(
                level = 9,
                list = listOf(
                    FunctionActivity(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE
                    ),
                    FunctionActivity(
                        idFunctionActivity = 2,
                        idActivity = 1,
                        typeActivity = TypeActivity.TRANSHIPMENT
                    ),

                    FunctionActivity(
                        idFunctionActivity = 3,
                        idActivity = 1,
                        typeActivity = TypeActivity.IMPLEMENT
                    )
                ),
                flagMechanic = true,
                flagTire = true,
                flagReel = true
            )

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(typeListPMM.find { it.second == ITEM_NORMAL }!!)
            list.add(typeListPMM.find { it.second == PERFORMANCE }!!)
            list.add(typeListPMM.find { it.second == TRANSHIPMENT }!!)
            list.add(typeListPMM.find { it.second == IMPLEMENT }!!)
            list.add(typeListPMM.find { it.second == MECHANICAL }!!)
            list.add(typeListPMM.find { it.second == TIRE }!!)
            list.add(typeListPMM.find { it.second == REEL }!!)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenu(
                            id = 1,
                            descr = "ITEM 1",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 1 to PMM,
                        ),
                        ItemMenu(
                            id = 2,
                            descr = "ITEM 2",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 1 to PMM,
                        )
                    )
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            val itemList = result.getOrNull()!!
            assertEquals(
                itemList.size,
                2
            )
            val item1 = itemList[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.descr,
                "ITEM 1"
            )
            val item2 = itemList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.descr,
                "ITEM 2"
            )
        }

    @Test
    fun `Check return correct if function execute successfully, type equip is FERT, all item and with list - PMM`() =
        runTest {

            wheneverRegister(
                level = 9,
                list = listOf(
                    FunctionActivity(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE
                    ),
                    FunctionActivity(
                        idFunctionActivity = 2,
                        idActivity = 1,
                        typeActivity = TypeActivity.TRANSHIPMENT
                    ),

                    FunctionActivity(
                        idFunctionActivity = 3,
                        idActivity = 1,
                        typeActivity = TypeActivity.IMPLEMENT
                    )
                ),
                flagMechanic = true,
                flagTire = true,
                flagReel = true,
                typeEquip = TypeEquip.FERT
            )

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(typeListPMM.find { it.second == ITEM_NORMAL }!!)
            list.add(typeListPMM.find { it.second == FERTIGATION }!!)
            list.add(typeListPMM.find { it.second == MECHANICAL }!!)
            list.add(typeListPMM.find { it.second == TIRE }!!)
            list.add(typeListPMM.find { it.second == REEL }!!)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenu(
                            id = 1,
                            descr = "ITEM 1",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 1 to PMM,
                        ),
                        ItemMenu(
                            id = 2,
                            descr = "ITEM 2",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 1 to PMM,
                        )
                    )
                )
            )
            val result = usecase("pmm")
            assertEquals(
                result.isSuccess,
                true
            )
            val itemList = result.getOrNull()!!
            assertEquals(
                itemList.size,
                2
            )
            val item1 = itemList[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.descr,
                "ITEM 1"
            )
            val item2 = itemList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.descr,
                "ITEM 2"
            )
        }

    @Test
    fun `Check return failure if have error in ItemMenuRepository listByTypeList - ECM`() =
        runTest {
            whenever(
                itemMenuRepository.listByApp(2 to ECM)
            ).thenReturn(
                resultFailure(
                    "IItemMenuRepository.listByTypeList",
                    "-",
                    Exception()
                )
            )
            val result = usecase("ecm")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IItemMenuRepository.listByTypeList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if process execute successfully - ECM`() =
        runTest {
            whenever(
                itemMenuRepository.listByApp(2 to ECM)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenu(
                            id = 1,
                            descr = "ITEM 1",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 1 to ECM,
                        ),
                    )
                )
            )
            val result = usecase("ecm")
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val item = list[0]
            assertEquals(
                item.id,
                1
            )
            assertEquals(
                item.descr,
                "ITEM 1"
            )
            assertEquals(
                item.type.first,
                1
            )
            assertEquals(
                item.type.second,
                ITEM_NORMAL
            )
            assertEquals(
                item.function.first,
                1
            )
            assertEquals(
                item.function.second,
                WORK
            )
            assertEquals(
                item.app.first,
                1
            )
            assertEquals(
                item.app.second,
                ECM
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getCompostingHeader - PCOMP`() =
        runTest {
            whenever(
                motoMecRepository.getFlowCompostingHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getCompostingHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pcomp")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IMotoMecRepository.getCompostingHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemMenuRepository listByTypeList - PCOMP`() =
        runTest {
            whenever(
                motoMecRepository.getFlowCompostingHeader()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            whenever(
                itemMenuRepository.listByApp(3 to PCOMP_INPUT)
            ).thenReturn(
                resultFailure(
                    "IItemMenuRepository.listByTypeList",
                    "-",
                    Exception()
                )
            )
            val result = usecase("pcomp")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IItemMenuRepository.listByTypeList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if process execute successfully - PCOMP`() =
        runTest {
            whenever(
                motoMecRepository.getFlowCompostingHeader()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            whenever(
                itemMenuRepository.listByApp(3 to PCOMP_INPUT)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenu(
                            id = 1,
                            descr = "ITEM 1",
                            type = 1 to ITEM_NORMAL,
                            pos = 1,
                            function = 1 to WORK,
                            app = 3 to PCOMP_INPUT,
                        ),
                    )
                )
            )
            val result = usecase("pcomp")
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val item = list[0]
            assertEquals(
                item.id,
                1
            )
            assertEquals(
                item.descr,
                "ITEM 1"
            )
            assertEquals(
                item.type.first,
                1
            )
            assertEquals(
                item.type.second,
                ITEM_NORMAL
            )
            assertEquals(
                item.function.first,
                1
            )
            assertEquals(
                item.function.second,
                WORK
            )
            assertEquals(
                item.app.first,
                3
            )
            assertEquals(
                item.app.second,
                PCOMP_INPUT
            )
        }

    
    private suspend fun wheneverRegister(
        level: Int,
        flagMechanic: Boolean = false,
        flagTire: Boolean = false,
        flagReel: Boolean = false,
        typeEquip: TypeEquip = TypeEquip.NORMAL,
        list: List<FunctionActivity> = emptyList(),
    ) {

        whenever(
            motoMecRepository.getIdEquipHeader()
        ).thenReturn(
            Result.success(1)
        )

        if(level == 1) return

        whenever(
            motoMecRepository.getIdByHeaderOpen()
        ).thenReturn(
            Result.success(1)
        )

        if(level == 2) return

        whenever(
            equipRepository.getTypeEquipByIdEquip(1)
        ).thenReturn(
            Result.success(typeEquip)
        )

        if(level == 3) return

        whenever(
            motoMecRepository.getIdActivityHeader()
        ).thenReturn(
            Result.success(1)
        )

        if(level == 4) return

        whenever(
            functionActivityRepository.listByIdActivity(1)
        ).thenReturn(
            Result.success(list)
        )

        if(level == 5) return

        whenever(
            equipRepository.getFlagMechanicByIdEquip(1)
        ).thenReturn(
            Result.success(flagMechanic)
        )

        if(level == 6) return

        whenever(
            equipRepository.getFlagTireByIdEquip(1)
        ).thenReturn(
            Result.success(flagTire)
        )

        if(level == 7) return

        whenever(
            functionStopRepository.getIdStopByType(TypeStop.REEL)
        ).thenReturn(
            Result.success(1)
        )

        if(level == 8) return

        whenever(
            motoMecRepository.hasNoteByIdStopAndIdHeader(1, 1)
        ).thenReturn(
            Result.success(flagReel)
        )

        if(level == 9) return

    }

}