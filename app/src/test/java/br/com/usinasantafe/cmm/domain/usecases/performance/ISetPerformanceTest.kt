package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetPerformanceTest {

    private val performanceRepository = mock<PerformanceRepository>()
    private val usecase = ISetPerformance(
        performanceRepository = performanceRepository
    )

    @Test
    fun `Check return failure if value is incorrect`() =
        runTest {
            val result = usecase(1,"as1dfs52fda,5")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetPerformance -> stringToDouble"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.text.ParseException: Unparseable number: \"as1dfs52fda,5\""
            )
        }
    
    @Test
    fun `Check return failure if have error in MotoMecRepository updatePerformance`() =
        runTest {
            whenever(
                performanceRepository.update(
                    id = 1,
                    value = 50.0
                )
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.updatePerformance",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                value = "50,0"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetPerformance -> IMotoMecRepository.updatePerformance"
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
                performanceRepository.update(
                    id = 1,
                    value = 50.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                id = 1,
                value = "50,0"
            )
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