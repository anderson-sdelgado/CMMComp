package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Servico
import br.com.usinasantafe.cmm.domain.repositories.stable.ServicoRepository
import javax.inject.Inject

class IServicoRepository @Inject constructor() : ServicoRepository {

    override suspend fun addAll(list: List<Servico>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Servico>> {
        TODO("Not yet implemented")
    }
}