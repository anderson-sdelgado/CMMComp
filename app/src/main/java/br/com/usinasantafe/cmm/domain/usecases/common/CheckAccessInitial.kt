package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.UsecaseException
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
            if (resultCheckHasConfig.isFailure)
                return Result.failure(resultCheckHasConfig.exceptionOrNull()!!)
            val hasConfig = resultCheckHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(false)
            val resultGetFlagUpdate = configRepository.getFlagUpdate()
            if (resultGetFlagUpdate.isFailure)
                return Result.failure(resultGetFlagUpdate.exceptionOrNull()!!)
            val flagUpdate = resultGetFlagUpdate.getOrNull()!!
            if (flagUpdate == FlagUpdate.OUTDATED)
                return Result.success(false)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckAccessInitial",
                    cause = e
                )
            )
        }
    }

}