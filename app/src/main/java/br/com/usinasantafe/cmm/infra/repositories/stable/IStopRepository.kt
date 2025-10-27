package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.StopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.StopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityStopToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IStopRepository @Inject constructor(
    private val stopRetrofitDatasource: StopRetrofitDatasource,
    private val stopRoomDatasource: StopRoomDatasource
) : StopRepository {

    override suspend fun addAll(list: List<Stop>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityStopToRoomModel() }
            val result = stopRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
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
        val result = stopRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(
        token: String
    ): Result<List<Stop>> {
        try {
            val result = stopRetrofitDatasource.listAll(token)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
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

    override suspend fun listByIdList(idList: List<Int>): Result<List<Stop>> {
        try {
            val result = stopRoomDatasource.listByIdList(idList)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
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

    override suspend fun getById(id: Int): Result<Stop> {
        try {
            val result = stopRoomDatasource.getById(id)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }

            return Result.success(result.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
