package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Component
import br.com.usinasantafe.cmm.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ComponentRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ComponentRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IComponentRepository @Inject constructor(
    private val componentRoomDatasource: ComponentRoomDatasource,
    private val componentRetrofitDatasource: ComponentRetrofitDatasource
): ComponentRepository {

    override suspend fun addAll(list: List<Component>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            componentRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            componentRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<Component>> =
        call(getClassAndMethod()) {
            val modelList = componentRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}