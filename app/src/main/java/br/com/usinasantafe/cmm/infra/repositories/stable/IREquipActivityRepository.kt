package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipActivityRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipActivityRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityREquipActivityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IREquipActivityRepository @Inject constructor(
    private val rEquipActivityRetrofitDatasource: REquipActivityRetrofitDatasource,
    private val rEquipActivityRoomDatasource: REquipActivityRoomDatasource
) : REquipActivityRepository {

    override suspend fun addAll(list: List<REquipActivity>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityREquipActivityToRoomModel() }
            rEquipActivityRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            rEquipActivityRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<REquipActivity>> =
        call(getClassAndMethod()) {
            val modelList = rEquipActivityRetrofitDatasource.listByIdEquip(token, idEquip).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByIdEquip(idEquip: Int): Result<List<REquipActivity>> =
        call(getClassAndMethod()) {
            val modelList = rEquipActivityRoomDatasource.listByIdEquip(idEquip).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }
}
