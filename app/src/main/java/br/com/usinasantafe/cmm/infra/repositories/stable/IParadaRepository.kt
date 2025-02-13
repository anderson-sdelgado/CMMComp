package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Parada
import br.com.usinasantafe.cmm.domain.repositories.stable.ParadaRepository
import javax.inject.Inject

class IParadaRepository @Inject constructor() : ParadaRepository {

    override suspend fun addAll(list: List<Parada>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Parada>> {
        TODO("Not yet implemented")
    }
}