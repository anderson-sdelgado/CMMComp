package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import javax.inject.Inject

class IItemOSMechanicRepository @Inject constructor(

): ItemOSMechanicRepository {
    override suspend fun addAll(list: List<ItemOSMechanic>): EmptyResult {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun listByIdEquipAndNroOS(
        token: String,
        idEquip: Int,
        nroOS: Int
    ): Result<List<ItemOSMechanic>> {
        TODO("Not yet implemented")
    }
}