package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Service
import br.com.usinasantafe.cmm.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ServiceRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ServiceRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IServiceRepository @Inject constructor(
    private val serviceRetrofitDatasource: ServiceRetrofitDatasource,
    private val serviceRoomDatasource: ServiceRoomDatasource
): ServiceRepository {

    override suspend fun addAll(list: List<Service>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            serviceRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            serviceRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<Service>> =
        call(getClassAndMethod()) {
            val modelList = serviceRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }
}