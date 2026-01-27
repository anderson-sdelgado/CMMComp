package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetIdEquipMotorPumpTest {

    private val equipRepository = mock<EquipRepository>()
    private val motoMecRepository = mock<MotoMecRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = ISetIdEquipMotorPump(
        equipRepository = equipRepository,
        motoMecRepository = motoMecRepository,
        startWorkManager = startWorkManager
    )

    @Test
    fun `Check return failure if nroEquip is incorrect`() =
        runTest {
            val result = usecase("2dfas200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipMotorPump -> toLong"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"2dfas200\""
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getIdByNro`() =
        runTest {
            whenever(
                equipRepository.getIdByNro(2200)
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdByNro",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipMotorPump -> IEquipRepository.getIdByNro"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setIdEquipMotorPumpHeader`() =
        runTest {
            whenever(
                equipRepository.getIdByNro(2200)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                motoMecRepository.setIdEquipMotorPumpHeader(20)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdEquipMotorPumpHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipMotorPump -> IMotoMecRepository.setIdEquipMotorPumpHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository saveHeader`() =
        runTest {
            whenever(
                equipRepository.getIdByNro(2200)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                motoMecRepository.setIdEquipMotorPumpHeader(20)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipMotorPump -> IMotoMecRepository.saveHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRepository.getIdByNro(2200)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                motoMecRepository.setIdEquipMotorPumpHeader(20)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveHeader()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase("2200")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

}