package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetConfigInternalTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = IGetConfigInternal(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository hasConfig`() =
        runTest {
            whenever(
                configRepository.hasConfig()
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
                "IGetConfigInternal -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return null if haven't data in Config table internal`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getConfig`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
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
                "IGetConfigInternal -> Unknown Error"
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
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.get()
            ).thenReturn(
                Result.success(
                    Config(
                        number = 16997417840,
                        password = "12345",
                        nroEquip = 310,
                        checkMotoMec = true,
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val configModel = result.getOrNull()!!
            assertEquals(
                configModel.number,
                "16997417840"
            )
            assertEquals(
                configModel.password,
                "12345"
            )
            assertEquals(
                configModel.nroEquip,
                "310"
            )
            assertEquals(
                configModel.checkMotoMec,
                true
            )
        }

}