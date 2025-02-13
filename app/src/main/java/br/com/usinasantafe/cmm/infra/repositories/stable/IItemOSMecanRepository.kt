package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMecan
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMecanRepository
import javax.inject.Inject

class IItemOSMecanRepository @Inject constructor() : ItemOSMecanRepository {

    override suspend fun addAll(list: List<ItemOSMecan>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<ItemOSMecan>> {
        TODO("Not yet implemented")
    }
}