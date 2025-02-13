package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Colab
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import javax.inject.Inject

class IColabRepository @Inject constructor() : ColabRepository {

    override suspend fun addAll(list: List<Colab>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Colab>> {
        TODO("Not yet implemented")
    }
}