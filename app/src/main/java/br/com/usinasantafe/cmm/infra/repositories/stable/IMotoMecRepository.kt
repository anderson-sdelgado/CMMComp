package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.MotoMec
import br.com.usinasantafe.cmm.domain.repositories.stable.MotoMecRepository
import javax.inject.Inject

class IMotoMecRepository @Inject constructor() : MotoMecRepository {

    override suspend fun addAll(list: List<MotoMec>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<MotoMec>> {
        TODO("Not yet implemented")
    }
}