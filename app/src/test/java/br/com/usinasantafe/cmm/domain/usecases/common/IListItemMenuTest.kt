package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuPMMRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.FunctionItemMenu
import br.com.usinasantafe.cmm.utils.TypeActivity
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.TypeView
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IListItemMenuTest {

    private val itemMenuPMMRepository = mock<ItemMenuPMMRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val functionActivityRepository = mock<FunctionActivityRepository>()
    private val functionStopRepository = mock<FunctionStopRepository>()
    private val usecase = IListItemMenu(
        itemMenuPMMRepository = itemMenuPMMRepository,
        configRepository = configRepository,
        motoMecRepository = motoMecRepository,
        equipRepository = equipRepository,
        functionActivityRepository = functionActivityRepository,
        functionStopRepository = functionStopRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getIdEquip`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListItemMenu -> IConfigRepository.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdEquipHeader`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdEquipHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdEquipHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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

            val list: MutableList<FunctionItemMenu> = mutableListOf()
            list.add(FunctionItemMenu.ITEM_NORMAL)
            list.add(FunctionItemMenu.FINISH_HEADER)

            whenever(
                itemMenuPMMRepository.listByTypeList(list)
            ).thenReturn(
                resultFailure(
                    "IItemMenuPMMRepository.listByTypeList",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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

            val list: MutableList<FunctionItemMenu> = mutableListOf()
            list.add(FunctionItemMenu.ITEM_NORMAL)
            list.add(FunctionItemMenu.FINISH_HEADER)

            whenever(
                itemMenuPMMRepository.listByTypeList(list)
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = usecase()
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
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
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

            val list: MutableList<FunctionItemMenu> = mutableListOf()
            list.add(FunctionItemMenu.ITEM_NORMAL)
            list.add(FunctionItemMenu.FINISH_HEADER)

            whenever(
                itemMenuPMMRepository.listByTypeList(list)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuPMM(
                            id = 1,
                            title = "ITEM 1",
                            function = FunctionItemMenu.ITEM_NORMAL
                        ),
                        ItemMenuPMM(
                            id = 2,
                            title = "ITEM 2",
                            function = FunctionItemMenu.FINISH_HEADER
                        )
                    )
                )
            )
            val result = usecase()
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
                item1.title,
                "ITEM 1"
            )
            assertEquals(
                item1.type,
                TypeView.ITEM
            )
            val item2 = itemList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.title,
                "ITEM 2"
            )
            assertEquals(
                item2.type,
                TypeView.BUTTON
            )
        }

    @Test
    fun `Check return correct if function execute successfully, type equip is NORMAL, all item and with list`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(2)
            )
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

            val list: MutableList<FunctionItemMenu> = mutableListOf()
            list.add(FunctionItemMenu.ITEM_NORMAL)
            list.add(FunctionItemMenu.PERFORMANCE)
            list.add(FunctionItemMenu.TRANSHIPMENT)
            list.add(FunctionItemMenu.IMPLEMENT)
            list.add(FunctionItemMenu.MECHANICAL_MAINTENANCE)
            list.add(FunctionItemMenu.TIRE)
            list.add(FunctionItemMenu.REEL)
            list.add(FunctionItemMenu.RETURN_LIST)

            whenever(
                itemMenuPMMRepository.listByTypeList(list)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuPMM(
                            id = 1,
                            title = "ITEM 1",
                            function = FunctionItemMenu.ITEM_NORMAL
                        ),
                        ItemMenuPMM(
                            id = 2,
                            title = "ITEM 2",
                            function = FunctionItemMenu.RETURN_LIST
                        )
                    )
                )
            )
            val result = usecase()
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
                item1.title,
                "ITEM 1"
            )
            assertEquals(
                item1.type,
                TypeView.ITEM
            )
            val item2 = itemList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.title,
                "ITEM 2"
            )
            assertEquals(
                item2.type,
                TypeView.BUTTON
            )
        }

    @Test
    fun `Check return correct if function execute successfully, type equip is FERT, all item and with list`() =
        runTest {
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(2)
            )
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

            val list: MutableList<FunctionItemMenu> = mutableListOf()
            list.add(FunctionItemMenu.ITEM_NORMAL)
            list.add(FunctionItemMenu.HOSE_COLLECTION)
            list.add(FunctionItemMenu.MECHANICAL_MAINTENANCE)
            list.add(FunctionItemMenu.TIRE)
            list.add(FunctionItemMenu.REEL)
            list.add(FunctionItemMenu.RETURN_LIST)

            whenever(
                itemMenuPMMRepository.listByTypeList(list)
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuPMM(
                            id = 1,
                            title = "ITEM 1",
                            function = FunctionItemMenu.ITEM_NORMAL
                        ),
                        ItemMenuPMM(
                            id = 2,
                            title = "ITEM 2",
                            function = FunctionItemMenu.RETURN_LIST
                        )
                    )
                )
            )
            val result = usecase()
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
                item1.title,
                "ITEM 1"
            )
            assertEquals(
                item1.type,
                TypeView.ITEM
            )
            val item2 = itemList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.title,
                "ITEM 2"
            )
            assertEquals(
                item2.type,
                TypeView.BUTTON
            )
        }
}