package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetNroOSTest {

    private val mechanicRepository = mock<MechanicRepository>()
    private val usecase = ISetNroOS(
        mechanicRepository = mechanicRepository
    )

    @Test
    fun `Check return failure if value of field is incorrect`() =
        runTest {
            val result = usecase(
                nroOS = "19759a"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOS -> toInt"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759a\""
            )
        }

    @Test
    fun `Check return failure if have error in MechanicRepository setNroOS`() =
        runTest {
            whenever(
                mechanicRepository.setNroOS(123456)
            ).thenReturn(
                resultFailure(
                    "IMechanicRepository.setNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOS -> IMechanicRepository.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val result = usecase("123456")
            verify(mechanicRepository, atLeastOnce()).setNroOS(123456)
            assertEquals(
                result.isSuccess,
                true
            )
        }

}