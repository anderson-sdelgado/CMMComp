package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.UsecaseException
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

interface CheckPassword {
    suspend operator fun invoke(password: String): Result<Boolean>
}

class ICheckPassword @Inject constructor(
    private val configRepository: ConfigRepository
): CheckPassword {

    override suspend fun invoke(password: String): Result<Boolean> {
        try {
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure)
                return Result.failure(resultHasConfig.exceptionOrNull()!!)
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(true)
            val resultPassword = configRepository.getPassword()
            if (resultPassword.isFailure)
                return Result.failure(resultPassword.exceptionOrNull()!!)
            val passwordConfig = resultPassword.getOrNull()!!
            if (passwordConfig == password)
                return Result.success(true)
            return Result.success(false)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckPassword",
                    cause = e
                )
            )
        }
    }

}