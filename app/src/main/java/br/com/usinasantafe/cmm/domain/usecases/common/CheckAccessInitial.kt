package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
            resultCheckHasConfig.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val hasConfig = resultCheckHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(false)
            val resultGetFlagUpdate = configRepository.getFlagUpdate()
            resultGetFlagUpdate.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val flagUpdate = resultGetFlagUpdate.getOrNull()!!
            val check = flagUpdate == FlagUpdate.UPDATED
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}