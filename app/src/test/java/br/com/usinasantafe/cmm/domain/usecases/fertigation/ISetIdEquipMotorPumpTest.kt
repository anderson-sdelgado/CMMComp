package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetIdEquipMotorPumpTest {

    private val equipRepository = Mockito.mock<EquipRepository>()
    private val motoMecRepository = Mockito.mock<MotoMecRepository>()
    private val startWorkManager = Mockito.mock<StartWorkManager>()
    private val fertigationRepository = Mockito.mock<FertigationRepository>()
    private val usecase = ISetIdEquipMotorPump(
        equipRepository = equipRepository,
        motoMecRepository = motoMecRepository,
        startWorkManager = startWorkManager,
        fertigationRepository = fertigationRepository
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
    fun `Check return failure if have error in EquipRepository getHourMeter`() =
        runTest {
            whenever(
                equipRepository.getIdByNro(2200)
            ).thenReturn(
                Result.success(20)
            )
            whenever(
                equipRepository.getHourMeter()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getHourMeter",
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
                "ISetIdEquipMotorPump -> IEquipRepository.getHourMeter"
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
                equipRepository.getHourMeter()
            ).thenReturn(
                Result.success(10000.0)
            )
            whenever(
                fertigationRepository.setIdEquipMotorPump(20)
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.setIdEquipMotorPumpHeader",
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
                "ISetIdEquipMotorPump -> IFertigationRepository.setIdEquipMotorPumpHeader"
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
                equipRepository.getHourMeter()
            ).thenReturn(
                Result.success(10000.0)
            )
            whenever(
                motoMecRepository.saveHeader(10000.0)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2200")
            verify(fertigationRepository, atLeastOnce()).setIdEquipMotorPump(20)
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
                equipRepository.getHourMeter()
            ).thenReturn(
                Result.success(10000.0)
            )
            val result = usecase("2200")
            verify(fertigationRepository, atLeastOnce()).setIdEquipMotorPump(20)
            verify(motoMecRepository, atLeastOnce()).saveHeader(10000.0)
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