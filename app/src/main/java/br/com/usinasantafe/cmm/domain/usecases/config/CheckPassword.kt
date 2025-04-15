package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
            if (resultHasConfig.isFailure) {
                val e = resultHasConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckPassword",
                    message = e.message,
                    cause = e.cause
                )
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(true)
            val resultPassword = configRepository.getPassword()
            if (resultPassword.isFailure){
                val e = resultPassword.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckPassword",
                    message = e.message,
                    cause = e.cause
                )
            }
            val passwordConfig = resultPassword.getOrNull()!!
            if (passwordConfig == password)
                return Result.success(true)
            return Result.success(false)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckPassword",
                message = "-",
                cause = e
            )
        }
    }

}