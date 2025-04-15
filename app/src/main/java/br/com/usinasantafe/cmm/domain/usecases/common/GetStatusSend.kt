package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.StatusSend
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
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetStatusSend",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.statusSend)
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetStatusSend",
                message = "-",
                    cause = e
            )
        }
    }

}