package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckAccessInitial {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessInitial @Inject constructor(
    private val configRepository: ConfigRepository
): CheckAccessInitial {

    override suspend fun invoke(): Result<Boolean> {
        return runCatching {
            val hasConfig = configRepository.hasConfig().getOrThrow()
            if (!hasConfig) return Result.success(false)
            if (!hasConfig) return Result.success(false)
            val flagUpdate = configRepository.getFlagUpdate().getOrThrow()
            flagUpdate == FlagUpdate.UPDATED
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}