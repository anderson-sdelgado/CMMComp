package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada
import br.com.usinasantafe.cmm.domain.repositories.stable.RFuncaoAtivParadaRepository
import javax.inject.Inject

class IRFuncaoAtivParadaRepository @Inject constructor() : RFuncaoAtivParadaRepository {

    override suspend fun addAll(list: List<RFuncaoAtivParada>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<RFuncaoAtivParada>> {
        TODO("Not yet implemented")
    }
}