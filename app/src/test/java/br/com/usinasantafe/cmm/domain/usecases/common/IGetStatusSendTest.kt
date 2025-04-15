package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetStatusSendTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = IGetStatusSend(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.failure(
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
                "IGetStatusSend -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        statusSend = StatusSend.SENT
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
                StatusSend.SENT
            )
        }

}