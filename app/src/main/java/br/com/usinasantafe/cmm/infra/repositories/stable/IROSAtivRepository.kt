package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSAtiv
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSAtivRepository
import javax.inject.Inject

class IROSAtivRepository @Inject constructor() : ROSAtivRepository {

    override suspend fun addAll(list: List<ROSAtiv>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<ROSAtiv>> {
        TODO("Not yet implemented")
    }
}