package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RAtivParada
import br.com.usinasantafe.cmm.domain.repositories.stable.RAtivParadaRepository
import javax.inject.Inject

class IRAtivParadaRepository @Inject constructor() : RAtivParadaRepository {

    override suspend fun addAll(list: List<RAtivParada>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<RAtivParada>> {
        TODO("Not yet implemented")
    }
}