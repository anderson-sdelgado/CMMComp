package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetValuePressureTest {

    private val fertigationRepository = mock<FertigationRepository>()
    private val usecase = ISetValuePressure(
        fertigationRepository = fertigationRepository
    )

    @Test
    fun `Check return failure if have error in FertigationRepository setValuePressure`() =
        runTest {
            whenever(
                fertigationRepository.setValuePressure(10.0)
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.setValuePressure",
                    "-",
                    Exception()
                )
            )
            val result = usecase(10.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetValuePressure -> IFertigationRepository.setValuePressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val result = usecase(10.0)
            verify(fertigationRepository, atLeastOnce()).setValuePressure(10.0)
            assertEquals(
                result.isSuccess,
                true
            )
        }

}