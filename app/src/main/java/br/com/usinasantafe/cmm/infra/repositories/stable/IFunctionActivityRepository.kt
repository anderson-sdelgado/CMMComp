package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IFunctionActivityRepository @Inject constructor(
    private val functionActivityRetrofitDatasource: FunctionActivityRetrofitDatasource,
    private val functionActivityRoomDatasource: FunctionActivityRoomDatasource
): FunctionActivityRepository {

    override suspend fun addAll(list: List<FunctionActivity>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            functionActivityRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            functionActivityRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<FunctionActivity>> =
        call(getClassAndMethod()) {
            val modelList = functionActivityRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listById(idActivity: Int): Result<List<FunctionActivity>> =
        call(getClassAndMethod()) {
            val modelList = functionActivityRoomDatasource.listById(idActivity).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun hasByIdAndType(
        idActivity: Int,
        typeActivity: TypeActivity
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            functionActivityRoomDatasource.hasByIdAndType(idActivity, typeActivity).getOrThrow()
        }
}