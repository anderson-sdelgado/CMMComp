package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.FERTIGATION
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.MECHANICAL
import br.com.usinasantafe.cmm.utils.PERFORMANCE
import br.com.usinasantafe.cmm.utils.PMM
import br.com.usinasantafe.cmm.utils.REEL
import br.com.usinasantafe.cmm.utils.TIRE
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.TypeActivity
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.WORK
import br.com.usinasantafe.cmm.utils.appList
import br.com.usinasantafe.cmm.utils.typeListPMM
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
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
    fun `Check return failure if have error in MotoMecRepository getIdEquipHeader`() =
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
                "IListItemMenu -> IMotoMecRepository.getIdEquipHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getTypeEquipByIdEquip`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
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
                "IListItemMenu -> IEquipRepository.getTypeEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdActivityHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
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
                "IListItemMenu -> IMotoMecRepository.getIdActivityHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityRepository listByIdActivity`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
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
                "IListItemMenu -> IFunctionActivityRepository.listByIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getFlagMechanicByIdEquip`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
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
                "IListItemMenu -> IEquipRepository.getFlagMechanicByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getFlagTireByIdEquip`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
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
                "IListItemMenu -> IEquipRepository.getFlagTireByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionStopRepository getIdStopByType`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
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
                "IListItemMenu -> IFunctionStopRepository.getIdStopByType"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository checkNoteHasByIdStop`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionStopRepository.getIdStopByType(TypeStop.REEL)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.checkNoteHasByIdStop(1)
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
                "IListItemMenu -> IMotoMecRepository.checkNoteHasByIdStop"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemMenuPMMRepository listByTypeList`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionStopRepository.getIdStopByType(TypeStop.REEL)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.checkNoteHasByIdStop(1)
            ).thenReturn(
                Result.success(false)
            )

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(1 to ITEM_NORMAL)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list,
                    app = app
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
                "IListItemMenu -> IItemMenuPMMRepository.listByTypeList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully, flow basic and emptyList`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionStopRepository.getIdStopByType(TypeStop.REEL)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.checkNoteHasByIdStop(1)
            ).thenReturn(
                Result.success(false)
            )

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(1 to ITEM_NORMAL)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list,
                    app = app
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
    fun `Check return correct if function execute successfully, flow basic and with list`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionStopRepository.getIdStopByType(TypeStop.REEL)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.checkNoteHasByIdStop(1)
            ).thenReturn(
                Result.success(false)
            )

            val list: MutableList<Pair<Int, String>> = mutableListOf()
            list.add(1 to ITEM_NORMAL)

            val app = appList.find { it.second == PMM }!!
            whenever(
                itemMenuRepository.listByTypeList(
                    typeList = list,
                    app = app
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
    fun `Check return correct if function execute successfully, type equip is NORMAL, all item and with list`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(
                    listOf(
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
                    )
                )
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionStopRepository.getIdStopByType(TypeStop.REEL)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.checkNoteHasByIdStop(1)
            ).thenReturn(
                Result.success(true)
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
                    typeList = list,
                    app = app
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
    fun `Check return correct if function execute successfully, type equip is FERT, all item and with list`() =
        runTest {
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.FERT)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(
                    listOf(
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
                    )
                )
            )
            whenever(
                equipRepository.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                equipRepository.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionStopRepository.getIdStopByType(TypeStop.REEL)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.checkNoteHasByIdStop(1)
            ).thenReturn(
                Result.success(true)
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
                    typeList = list,
                    app = app
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
}