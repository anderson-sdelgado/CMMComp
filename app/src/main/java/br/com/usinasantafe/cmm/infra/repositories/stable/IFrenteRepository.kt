package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Frente
import br.com.usinasantafe.cmm.domain.repositories.stable.FrenteRepository
import javax.inject.Inject

class IFrenteRepository @Inject constructor() : FrenteRepository {

    override suspend fun addAll(list: List<Frente>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Frente>> {
        TODO("Not yet implemented")
    }
}