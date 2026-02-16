package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RActivityStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IRActivityStopRepository @Inject constructor(
    private val rActivityStopRetrofitDatasource: RActivityStopRetrofitDatasource,
    private val rActivityStopRoomDatasource: RActivityStopRoomDatasource
) : RActivityStopRepository {

    override suspend fun addAll(list: List<RActivityStop>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityToRoomModel() }
            rActivityStopRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            rActivityStopRoomDatasource.deleteAll().getOrThrow()
        }


    override suspend fun listAll(
        token: String
    ): Result<List<RActivityStop>> =
        call(getClassAndMethod()) {
            val modelList = rActivityStopRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByIdActivity(idActivity: Int): Result<List<RActivityStop>> =
        call(getClassAndMethod()) {
            val modelList = rActivityStopRoomDatasource.listByIdActivity(idActivity).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }
}
