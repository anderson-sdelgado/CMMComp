package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemOSMechanicRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemOSMechanicRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemOSMechanicRepository @Inject constructor(
    private val itemOSMechanicRetrofitDatasource: ItemOSMechanicRetrofitDatasource,
    private val itemOSMechanicRoomDatasource: ItemOSMechanicRoomDatasource
): ItemOSMechanicRepository {

    override suspend fun addAll(list: List<ItemOSMechanic>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            itemOSMechanicRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): Result<Unit> =
        call(getClassAndMethod()) {
            itemOSMechanicRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listByIdEquipAndNroOS(
        token: String,
        idEquip: Int,
        nroOS: Int
    ): Result<List<ItemOSMechanic>> =
        call(getClassAndMethod()) {
            val modelList = itemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS(token, nroOS, idEquip).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}