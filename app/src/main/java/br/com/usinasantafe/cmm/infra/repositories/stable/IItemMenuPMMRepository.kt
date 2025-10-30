package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuPMMRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemMenuPMMRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuPMMRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityItemMenuPMMToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.FunctionItemMenu
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemMenuPMMRepository  @Inject constructor(
    private val itemMenuPMMRetrofitDatasource: ItemMenuPMMRetrofitDatasource,
    private val itemMenuPMMRoomDatasource: ItemMenuPMMRoomDatasource
): ItemMenuPMMRepository {
    override suspend fun addAll(list: List<ItemMenuPMM>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityItemMenuPMMToRoomModel() }
            val result = itemMenuPMMRoomDatasource.addAll(roomModelList)
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
        val result = itemMenuPMMRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<ItemMenuPMM>> {
        try {
            val result = itemMenuPMMRetrofitDatasource.listAll(token)
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

    override suspend fun listByTypeList(typeList: List<FunctionItemMenu>): Result<List<ItemMenuPMM>> {
        try {
            val result = itemMenuPMMRoomDatasource.listByTypeList(typeList)
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
}