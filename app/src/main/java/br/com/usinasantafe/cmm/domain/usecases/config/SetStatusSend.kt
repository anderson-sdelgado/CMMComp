package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetStatusSend {
    suspend operator fun invoke(statusSend: StatusSend): Result<Boolean>
}

class ISetStatusSend @Inject constructor(
    private val configRepository: ConfigRepository
): SetStatusSend {

    override suspend fun invoke(statusSend: StatusSend): Result<Boolean> {
        val result = configRepository.setStatusSend(statusSend)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

}