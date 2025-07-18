package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultHasConfig.exceptionOrNull()!!
                )
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(true)
            val resultGetPassword = configRepository.getPassword()
            if (resultGetPassword.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetPassword.exceptionOrNull()!!
                )
            }
            val passwordConfig = resultGetPassword.getOrNull()!!
            if (passwordConfig == password)
                return Result.success(true)
            return Result.success(false)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}