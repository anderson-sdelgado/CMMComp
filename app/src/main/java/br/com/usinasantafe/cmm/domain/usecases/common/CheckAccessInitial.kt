package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.FlagUpdate
import javax.inject.Inject

interface CheckAccessInitial {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessInitial @Inject constructor(
    private val configRepository: ConfigRepository
): CheckAccessInitial {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultCheckHasConfig = configRepository.hasConfig()
            if (resultCheckHasConfig.isFailure){
                val e = resultCheckHasConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckAccessInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val hasConfig = resultCheckHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(false)
            val resultGetFlagUpdate = configRepository.getFlagUpdate()
            if (resultGetFlagUpdate.isFailure) {
                val e = resultGetFlagUpdate.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckAccessInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val flagUpdate = resultGetFlagUpdate.getOrNull()!!
            if (flagUpdate == FlagUpdate.OUTDATED)
                return Result.success(false)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckAccessInitial",
                message = "-",
                cause = e
            )
        }
    }

}