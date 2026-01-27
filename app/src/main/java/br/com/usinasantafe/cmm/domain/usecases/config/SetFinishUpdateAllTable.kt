package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetFinishUpdateAllTable {
    suspend operator fun invoke(): EmptyResult
}

class ISetFinishUpdateAllTable @Inject constructor(
    private val configRepository: ConfigRepository
): SetFinishUpdateAllTable {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            configRepository.setFlagUpdate(FlagUpdate.UPDATED).getOrThrow()
        }

}