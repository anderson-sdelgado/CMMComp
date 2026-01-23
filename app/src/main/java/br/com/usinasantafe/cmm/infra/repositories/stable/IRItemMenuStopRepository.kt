package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RItemMenuStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RItemMenuStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityRItemMenuStopToRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IRItemMenuStopRepository @Inject constructor(
    private val rItemMenuStopRetrofitDatasource: RItemMenuStopRetrofitDatasource,
    private val rItemMenuStopRoomDatasource: RItemMenuStopRoomDatasource
) : RItemMenuStopRepository {

    override suspend fun addAll(list: List<RItemMenuStop>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityRItemMenuStopToRoomModel() }
            val result = rItemMenuStopRoomDatasource.addAll(roomModelList)
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
        val result = rItemMenuStopRoomDatasource.deleteAll()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<RItemMenuStop>> {
        try {
            val result = rItemMenuStopRetrofitDatasource.listAll(token)
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

    override suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?> {
        val result = rItemMenuStopRoomDatasource.getIdStopByIdFunctionAndIdApp(
            idFunction = idFunction,
            idApp = idApp
        )
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}