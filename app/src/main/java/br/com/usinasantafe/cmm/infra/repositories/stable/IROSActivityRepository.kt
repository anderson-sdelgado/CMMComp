package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IROSActivityRepository @Inject constructor(
    private val rOSActivityRetrofitDatasource: ROSActivityRetrofitDatasource,
    private val rOSActivityRoomDatasource: ROSActivityRoomDatasource
): ROSActivityRepository {

    override suspend fun addAll(list: List<ROSActivity>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityToRoomModel() }
            rOSActivityRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            rOSActivityRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(
        token: String
    ): Result<List<ROSActivity>> =
        call(getClassAndMethod()) {
            val modelList = rOSActivityRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSActivity>> =
        call(getClassAndMethod()) {
            val modelList = rOSActivityRetrofitDatasource.listByNroOS(token, nroOS).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByIdOS(idOS: Int): Result<List<ROSActivity>> =
        call(getClassAndMethod()) {
            val modelList = rOSActivityRoomDatasource.listByIdOS(idOS).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }
}
