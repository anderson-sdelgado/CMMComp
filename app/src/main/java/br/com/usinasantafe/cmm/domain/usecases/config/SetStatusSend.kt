package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetStatusSend {
    suspend operator fun invoke(statusSend: StatusSend): EmptyResult
}

class ISetStatusSend @Inject constructor(
    private val configRepository: ConfigRepository
): SetStatusSend {

    override suspend fun invoke(statusSend: StatusSend): EmptyResult =
        call(getClassAndMethod()) {
            configRepository.setStatusSend(statusSend).getOrThrow()
        }

}