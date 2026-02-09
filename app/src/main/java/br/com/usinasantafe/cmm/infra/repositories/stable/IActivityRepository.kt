package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.BaseStableRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IActivityRepository @Inject constructor(
    private val activityRetrofitDatasource: ActivityRetrofitDatasource,
    private val activityRoomDatasource: ActivityRoomDatasource
) : BaseStableRepository<
        Activity,
        ActivityRetrofitModel,
        ActivityRoomModel
    >(
        retrofitListAll = activityRetrofitDatasource::listAll,
        roomAddAll = activityRoomDatasource::addAll,
        roomDeleteAll = activityRoomDatasource::deleteAll,
        retrofitToEntity = ActivityRetrofitModel::retrofitModelToEntity,
        entityToRoom = Activity::entityToRoomModel,
        classAndMethod = getClassAndMethod()
    ),
    ActivityRepository {

    override suspend fun listByIdList(idList: List<Int>): Result<List<Activity>> =
        call(getClassAndMethod()) {
            val roomModelList = activityRoomDatasource.listByIdList(idList).getOrThrow()
            roomModelList.map { it.roomModelToEntity() }
        }

    override suspend fun getById(id: Int): Result<Activity> =
        call(getClassAndMethod()) {
            activityRoomDatasource.getById(id).getOrThrow().roomModelToEntity()
        }

}