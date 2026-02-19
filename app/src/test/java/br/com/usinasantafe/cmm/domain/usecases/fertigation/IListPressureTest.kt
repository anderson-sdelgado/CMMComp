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

class IListPressureTest {

    private val pressureRepository = mock<PressureRepository>()
    private val fertigationRepository = mock<FertigationRepository>()
    private val usecase = IListPressure(
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
                "IListPressure -> IFertigationRepository.getIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PressureRepository listByIdNozzle`() =
        runTest {
            whenever(
                fertigationRepository.getIdNozzle()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                pressureRepository.listByIdNozzle(1)
            ).thenReturn(
                resultFailure(
                    "IPressureRepository.listByIdNozzle",
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
                "IListPressure -> IPressureRepository.listByIdNozzle"
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
                pressureRepository.listByIdNozzle(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        Pressure(
                            id = 1,
                            idNozzle = 1,
                            value = 1.0,
                            speed = 1
                        ),
                        Pressure(
                            id = 2,
                            idNozzle = 1,
                            value = 1.0,
                            speed = 2
                        ),
                        Pressure(
                            id = 3,
                            idNozzle = 1,
                            value = 2.0,
                            speed = 10
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
                listOf(1.0, 2.0)
            )
        }

}