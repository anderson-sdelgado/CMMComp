package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.token
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetTokenTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = IGetToken(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository get`() =
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
                "IGetToken -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if get return config with field empty`() =
        runTest {
            val config = Config(
                app = "PMM",
                idBD = 1,
                nroEquip = 1,
                number = 1
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val config = Config(
                app = "PMM",
                idBD = 1,
                nroEquip = 1,
                number = 1,
                version = "1.00"
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(config)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val token = token(
                app = config.app!!,
                idBD = config.idBD!!,
                nroEquip = config.nroEquip!!,
                number = config.number!!,
                version = config.version!!
            )
            assertEquals(
                result.getOrNull()!!,
                token
            )
        }

}