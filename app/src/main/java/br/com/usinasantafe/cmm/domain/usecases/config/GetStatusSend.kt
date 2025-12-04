package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetStatusSend {
    suspend operator fun invoke(): Result<StatusSend>
}

class IGetStatusSend @Inject constructor(
    private val configRepository: ConfigRepository
): GetStatusSend {

    override suspend fun invoke(): Result<StatusSend> {
        try {
            val result = configRepository.get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.statusSend)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}