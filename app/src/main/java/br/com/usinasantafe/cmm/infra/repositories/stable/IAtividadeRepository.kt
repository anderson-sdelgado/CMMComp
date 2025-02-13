package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Atividade
import br.com.usinasantafe.cmm.domain.repositories.stable.AtividadeRepository
import javax.inject.Inject

class IAtividadeRepository @Inject constructor() : AtividadeRepository {

    override suspend fun addAll(list: List<Atividade>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Atividade>> {
        TODO("Not yet implemented")
    }

}