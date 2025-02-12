package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.utils.FlagUpdate
import javax.inject.Inject

interface SetCheckUpdateAllTable {
    suspend operator fun invoke(flagUpdate: FlagUpdate): Result<Boolean>
}

class ISetCheckUpdateAllTable @Inject constructor(): SetCheckUpdateAllTable {

    override suspend fun invoke(flagUpdate: FlagUpdate): Result<Boolean> {
        TODO("Not yet implemented")
    }

}