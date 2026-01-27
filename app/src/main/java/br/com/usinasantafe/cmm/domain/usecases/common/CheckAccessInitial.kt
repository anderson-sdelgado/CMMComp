package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

interface CheckAccessInitial {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessInitial @Inject constructor(
    private val configRepository: ConfigRepository
): CheckAccessInitial {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val hasConfig = configRepository.hasConfig().getOrThrow()
            if (!hasConfig) return@call false
            val flagUpdate = configRepository.getFlagUpdate().getOrThrow()
            flagUpdate == FlagUpdate.UPDATED
        }

}