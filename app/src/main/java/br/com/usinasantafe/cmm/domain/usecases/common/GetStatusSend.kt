package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.UsecaseException
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
            val result = configRepository.getConfig()
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            val config = result.getOrNull()!!
            return Result.success(config.statusSend)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetStatusSend",
                    cause = e
                )
            )
        }
    }

}