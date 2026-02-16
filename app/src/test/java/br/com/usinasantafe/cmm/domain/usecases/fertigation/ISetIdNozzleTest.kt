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

class ISetIdNozzleTest {

    private val fertigationRepository = mock<FertigationRepository>()
    private val usecase = ISetIdNozzle(
        fertigationRepository = fertigationRepository
    )

    @Test
    fun `Check return failure if have error in FertigationRepository setIdNozzle`() =
        runTest {
            whenever(
                fertigationRepository.setIdNozzle(1)
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.setIdNozzle",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdNozzle -> IFertigationRepository.setIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            usecase(1)
            verify(fertigationRepository, atLeastOnce()).setIdNozzle(1)
        }

}