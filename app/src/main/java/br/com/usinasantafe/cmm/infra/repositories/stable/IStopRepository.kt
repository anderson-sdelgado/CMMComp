package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.StopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.StopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IStopRepository @Inject constructor(
    private val stopRetrofitDatasource: StopRetrofitDatasource,
    private val stopRoomDatasource: StopRoomDatasource
) : StopRepository {

    override suspend fun addAll(list: List<Stop>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityToRoomModel() }
            stopRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            stopRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(
        token: String
    ): Result<List<Stop>> =
        call(getClassAndMethod()) {
            val modelList = stopRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByIdList(idList: List<Int>): Result<List<Stop>> =
        call(getClassAndMethod()) {
            val modelList = stopRoomDatasource.listByIdList(idList).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun getById(id: Int): Result<Stop> =
        call(getClassAndMethod()) {
            stopRoomDatasource.getById(id).getOrThrow().roomModelToEntity()
        }
}
