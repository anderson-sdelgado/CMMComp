package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckCloseCollectionTest {

    private val fertigationRepository = mock<FertigationRepository>()
    private val usecase = IHasCloseCollection(
        fertigationRepository = fertigationRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository hasCollectionByIdHeaderListAndValueNull`() =
        runTest {
            whenever(
                fertigationRepository.hasCollectionByValueNull()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.hasCollectionByValueNull",
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
                "ICheckCloseCollection -> IMotoMecRepository.hasCollectionByValueNull"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false and execute finishHeader if PerformanceRepository hasByIdHeaderAndValueNull return true`() =
        runTest {
            whenever(
                fertigationRepository.hasCollectionByValueNull()
            ).thenReturn(
                Result.success(true)
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
    fun `Check return true and not execute finishHeader if PerformanceRepository hasByIdHeaderAndValueNull return false`() =
        runTest {
            whenever(
                fertigationRepository.hasCollectionByValueNull()
            ).thenReturn(
                Result.success(false)
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