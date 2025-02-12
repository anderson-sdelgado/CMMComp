package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.RepositoryException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISendDataConfigTest {

    private val configRepository = mock<ConfigRepository>()
    private val sendDataConfig = ISendDataConfig(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if set value incorrect`() =
        runTest {
            val result = sendDataConfig(
                number = "dfas",
                password = "12345",
                nroEquip = "310",
                app = "pmm",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SendDataConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"dfas\""
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository send`() =
        runTest {
            whenever(
                configRepository.send(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        nroEquip = 310,
                        app = "PMM",
                        version = "1.00"
                    )
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.send",
                        cause = Exception()
                    )
                )
            )
            val result = sendDataConfig(
                number = "16997417840",
                password = "12345",
                nroEquip = "310",
                app = "pmm",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

}