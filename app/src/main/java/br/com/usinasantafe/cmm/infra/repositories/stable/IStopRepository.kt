package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.StopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.StopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.BaseStableRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IStopRepository @Inject constructor(
    private val stopRetrofitDatasource: StopRetrofitDatasource,
    private val stopRoomDatasource: StopRoomDatasource
) : BaseStableRepository<
        Stop,
        StopRetrofitModel,
        StopRoomModel
        >(
    retrofitListAll = stopRetrofitDatasource::listAll,
    roomAddAll = stopRoomDatasource::addAll,
    roomDeleteAll = stopRoomDatasource::deleteAll,
    retrofitToEntity = StopRetrofitModel::retrofitModelToEntity,
    entityToRoom = Stop::entityToRoomModel,
    classAndMethod = getClassAndMethod()
),
    StopRepository {

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
