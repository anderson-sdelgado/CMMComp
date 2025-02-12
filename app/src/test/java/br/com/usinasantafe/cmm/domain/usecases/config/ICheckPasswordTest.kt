package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.RepositoryException
import br.com.usinasantafe.cmm.domain.errors.UsecaseException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckPasswordTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ICheckPassword(
        configRepository = configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository hasConfig`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.hasConfig",
                        cause = Exception()
                    )
                )
            )
            val result = usecase("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.hasConfig"
            )
        }

    @Test
    fun `Check return true if have not data in Config table internal`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase("12345")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getPassword`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getPassword()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.getPassword",
                        cause = Exception()
                    )
                )
            )
            val result = usecase("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.getPassword"
            )
        }

    @Test
    fun `Check return true if input password equal password db`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getPassword()
            ).thenReturn(
                Result.success("12345")
            )
            val result = usecase("12345")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return false if input password different password table internal`() =
        runTest {
            whenever(
                configRepository.hasConfig()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                configRepository.getPassword()
            ).thenReturn(
                Result.success("12345")
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}