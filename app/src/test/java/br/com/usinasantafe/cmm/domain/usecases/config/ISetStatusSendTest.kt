package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetStatusSendTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISetStatusSend(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository setStatusSend`() =
        runTest {
            whenever(
                configRepository.setStatusSend(StatusSend.SEND)
            ).thenReturn(
                resultFailure(
                    context = "ISetStatusSend",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(StatusSend.SEND)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetStatusSend -> ISetStatusSend"
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
                configRepository.setStatusSend(StatusSend.SEND)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(StatusSend.SEND)
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