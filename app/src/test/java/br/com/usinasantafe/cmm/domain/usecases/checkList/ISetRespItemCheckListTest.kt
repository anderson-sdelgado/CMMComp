package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.cmm.utils.OptionRespCheckList
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetRespItemCheckListTest {

    private val checkListRepository = mock<CheckListRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val itemCheckListRepository = mock<ItemCheckListRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = ISetRespItemCheckList(
        checkListRepository = checkListRepository,
        configRepository = configRepository,
        equipRepository = equipRepository,
        itemCheckListRepository = itemCheckListRepository,
        startWorkManager = startWorkManager
    )

    @Test
    fun `Check return failure if have error in CheckListRepository cleanResp and pos equals 1`() =
        runTest {
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.cleanResp",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> ICheckListRepository.cleanResp"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository saveResp`() =
        runTest {
            val entity = RespItemCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.saveResp(entity)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.saveResp",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> ICheckListRepository.saveResp"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getIdEquip`() =
        runTest {
            val entity = RespItemCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.saveResp(entity)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> IConfigRepository.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getIdCheckListByIdEquip`() =
        runTest {
            val entity = RespItemCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.saveResp(entity)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdCheckListByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> IEquipRepository.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in ItemCheckListRepository countByIdCheckList`() =
        runTest {
            val entity = RespItemCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.saveResp(entity)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                itemCheckListRepository.countByIdCheckList(10)
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRepository.countByIdCheckList",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> IItemCheckListRepository.countByIdCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository saveCheckList and count equals pos`() =
        runTest {
            val entity = RespItemCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.saveResp(entity)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                itemCheckListRepository.countByIdCheckList(10)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                checkListRepository.saveCheckList()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.saveCheckList",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> ICheckListRepository.saveCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and count equals pos`() =
        runTest {
            val entity = RespItemCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.saveResp(entity)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                itemCheckListRepository.countByIdCheckList(10)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                checkListRepository.saveCheckList()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return correct if function execute successfully and pos minor than count`() =
        runTest {
            val entity = RespItemCheckList(
                idItem = 1,
                option = OptionRespCheckList.ACCORDING
            )
            whenever(
                checkListRepository.cleanResp()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkListRepository.saveResp(entity)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getIdEquip()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipRepository.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                itemCheckListRepository.countByIdCheckList(10)
            ).thenReturn(
                Result.success(2)
            )
            whenever(
                checkListRepository.saveCheckList()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.ACCORDING
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }


}