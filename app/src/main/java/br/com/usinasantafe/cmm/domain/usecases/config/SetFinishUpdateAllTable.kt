package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetFinishUpdateAllTable {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetFinishUpdateAllTable @Inject constructor(
    private val configRepository: ConfigRepository
): SetFinishUpdateAllTable {

    override suspend fun invoke(): Result<Boolean> {
        val result = configRepository.setFlagUpdate(FlagUpdate.UPDATED)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}