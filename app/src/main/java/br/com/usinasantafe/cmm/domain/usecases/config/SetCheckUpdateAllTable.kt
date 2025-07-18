package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetCheckUpdateAllTable {
    suspend operator fun invoke(flagUpdate: FlagUpdate): Result<Boolean>
}

class ISetCheckUpdateAllTable @Inject constructor(
    private val configRepository: ConfigRepository
): SetCheckUpdateAllTable {

    override suspend fun invoke(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val result = configRepository.setFlagUpdate(flagUpdate)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}