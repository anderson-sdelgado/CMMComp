package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Bocal
import br.com.usinasantafe.cmm.domain.repositories.stable.BocalRepository
import javax.inject.Inject

class IBocalRepository @Inject constructor() : BocalRepository {

    override suspend fun addAll(list: List<Bocal>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Bocal>> {
        TODO("Not yet implemented")
    }
}