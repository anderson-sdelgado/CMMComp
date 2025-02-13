package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Leira
import br.com.usinasantafe.cmm.domain.repositories.stable.LeiraRepository
import javax.inject.Inject

class ILeiraRepository @Inject constructor() : LeiraRepository {

    override suspend fun addAll(list: List<Leira>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Leira>> {
        TODO("Not yet implemented")
    }
}