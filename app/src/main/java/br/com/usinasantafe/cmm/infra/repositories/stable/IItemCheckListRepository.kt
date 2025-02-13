package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import javax.inject.Inject

class IItemCheckListRepository @Inject constructor() : ItemCheckListRepository {

    override suspend fun addAll(list: List<ItemCheckList>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<ItemCheckList>> {
        TODO("Not yet implemented")
    }
}