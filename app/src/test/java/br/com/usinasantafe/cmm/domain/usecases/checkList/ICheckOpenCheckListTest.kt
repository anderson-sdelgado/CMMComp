package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class ICheckOpenCheckListTest {

    private val configRepository = mock<ConfigRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ICheckOpenCheckList(
        configRepository = configRepository,
        equipRepository = equipRepository,
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in EquipRepository getIdCheckListByIdEquip`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                resultFailure(
                    context = "IEquipRepository.getIdCheckListByIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IEquipRepository.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if idCheckList is 0`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(0)
            )
            val result = usecase()
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
    fun `Check return failure if have error in ConfigRepository getIdTurnCheckListLast`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                resultFailure(
                    context = "IConfigRepository.getIdTurnCheckListLast",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IConfigRepository.getIdTurnCheckListLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    @Test
    fun `Check return true if idTurnCheckListLast is null`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(null)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdTurnHeader`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                resultFailure(
                    context = "IMotoMecRepository.getIdTurnHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IMotoMecRepository.getIdTurnHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if idTurnCheckListLast is different of idTurnCheckList`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(2)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getDateCheckListLast()`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getDateCheckListLast()
            ).thenReturn(
                resultFailure(
                    context = "IConfigRepository.getDateCheckListLast",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IConfigRepository.getDateCheckListLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if dateCheckListLast is different of date now`() =
        runTest {
            whenever(
                equipRepository.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                configRepository.getIdTurnCheckListLast()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdTurnHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                configRepository.getDateCheckListLast()
            ).thenReturn(
                Result.success(
                    Date(1750708222000)
                )
            )
            val result = usecase()
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