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

class ISetCollectionTest {

    private val fertigationRepository = mock<FertigationRepository>()
    private val usecase = ISetCollection(
        fertigationRepository = fertigationRepository
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
                "ISetCollection -> stringToDouble"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.text.ParseException: Unparseable number: \"as1dfs52fda,5\""
            )
        }

    @Test
    fun `Check return failure if have error in FertigationRepository updateCollection`() =
        runTest {
            whenever(
                fertigationRepository.updateCollection(
                    id = 1,
                    value = 50.0
                )
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.updateCollection",
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
                "ISetCollection -> IFertigationRepository.updateCollection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val result = usecase(
                id = 1,
                value = "50,0"
            )
            verify(fertigationRepository, atLeastOnce()).updateCollection(
                id = 1,
                value = 50.0
            )
            assertEquals(
                result.isSuccess,
                true
            )
        }

}