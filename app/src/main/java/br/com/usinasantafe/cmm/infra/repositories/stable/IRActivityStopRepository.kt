package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RActivityStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityRActivityStopToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IRActivityStopRepository @Inject constructor(
    private val rActivityStopRetrofitDatasource: RActivityStopRetrofitDatasource,
    private val rActivityStopRoomDatasource: RActivityStopRoomDatasource
) : RActivityStopRepository {

    override suspend fun addAll(list: List<RActivityStop>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityRActivityStopToRoomModel() }
            val result = rActivityStopRoomDatasource.addAll(roomModelList)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = rActivityStopRoomDatasource.deleteAll()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun listAll(
        token: String
    ): Result<List<RActivityStop>> {
        try {
            val result = rActivityStopRetrofitDatasource.listAll(token)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdActivity(idActivity: Int): Result<List<RActivityStop>> {
        try {
            val result = rActivityStopRoomDatasource.listByIdActivity(idActivity)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
