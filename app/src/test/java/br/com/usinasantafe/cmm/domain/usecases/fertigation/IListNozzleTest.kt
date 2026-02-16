package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.domain.repositories.stable.NozzleRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IListNozzleTest {

    private val nozzleRepository = mock<NozzleRepository>()
    private val usecase = IListNozzle(
        nozzleRepository = nozzleRepository
    )

    @Test
    fun `Check return failure if have error in NozzleRepository listAll`() =
        runTest {
            whenever(
                nozzleRepository.listAll()
            ).thenReturn(
                resultFailure(
                    "INozzleRepository.listAll",
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
                "IListNozzle -> INozzleRepository.listAll"
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
                nozzleRepository.listAll()
            ).thenReturn(
                Result.success(
                    listOf(
                        Nozzle(
                            id = 1,
                            cod = 1,
                            descr = "Item"
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
                    Nozzle(
                        id = 1,
                        cod = 1,
                        descr = "Item"
                    )
                )
            )
        }

}