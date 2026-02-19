package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.domain.repositories.stable.PressureRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IListSpeedTest {

    private val fertigationRepository = mock<FertigationRepository>()
    private val pressureRepository = mock<PressureRepository>()
    private val usecase = IListSpeed(
        fertigationRepository = fertigationRepository,
        pressureRepository = pressureRepository
    )

    @Test
    fun `Check return failure if have error in FertigationRepository getIdNozzle`() =
        runTest {
            whenever(
                fertigationRepository.getIdNozzle()
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.getIdNozzle",
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
                "IListSpeed -> IFertigationRepository.getIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FertigationRepository getValuePressure`() =
        runTest {
            whenever(
                fertigationRepository.getIdNozzle()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                fertigationRepository.getValuePressure()
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.getValuePressure",
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
                "IListSpeed -> IFertigationRepository.getValuePressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PressureRepository listByIdNozzleAndValuePressure`() =
        runTest {
            whenever(
                fertigationRepository.getIdNozzle()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                fertigationRepository.getValuePressure()
            ).thenReturn(
                Result.success(10.0)
            )
            whenever(
                pressureRepository.listByIdNozzleAndValuePressure(1, 10.0)
            ).thenReturn(
                resultFailure(
                    "IPressureRepository.listByIdNozzleAndValuePressure",
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
                "IListSpeed -> IPressureRepository.listByIdNozzleAndValuePressure"
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
                fertigationRepository.getIdNozzle()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                fertigationRepository.getValuePressure()
            ).thenReturn(
                Result.success(10.0)
            )
            whenever(
                pressureRepository.listByIdNozzleAndValuePressure(1, 10.0)
            ).thenReturn(
                Result.success(
                    listOf(
                        Pressure(
                            id = 1,
                            idNozzle = 1,
                            value = 10.0,
                            speed = 1
                        )
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Pressure(
                        id = 1,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    )
                )
            )
        }

}