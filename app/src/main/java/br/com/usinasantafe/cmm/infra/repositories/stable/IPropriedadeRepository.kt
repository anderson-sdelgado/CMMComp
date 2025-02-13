package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Propriedade
import br.com.usinasantafe.cmm.domain.repositories.stable.PropriedadeRepository
import javax.inject.Inject

class IPropriedadeRepository @Inject constructor() : PropriedadeRepository {

    override suspend fun addAll(list: List<Propriedade>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Propriedade>> {
        TODO("Not yet implemented")
    }
}