package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemCheckListRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemCheckListRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityItemCheckListToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemCheckListRepository @Inject constructor(
    private val itemCheckListRetrofitDatasource: ItemCheckListRetrofitDatasource,
    private val itemCheckListRoomDatasource: ItemCheckListRoomDatasource
) : ItemCheckListRepository {

    override suspend fun addAll(list: List<ItemCheckList>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityItemCheckListToRoomModel() }
            val result = itemCheckListRoomDatasource.addAll(roomModelList)
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
        val result = itemCheckListRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<List<ItemCheckList>> {
        try {
            val result = itemCheckListRetrofitDatasource.listByNroEquip(
                token = token,
                nroEquip = nroEquip
            )
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

    override suspend fun checkUpdateByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<Boolean> {
        try {
            val result = itemCheckListRetrofitDatasource.checkUpdateByNroEquip(
                token = token,
                nroEquip = nroEquip
            )
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.qtd > 0)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdCheckList(idCheckList: Int): Result<List<ItemCheckList>> {
        try {
            val result = itemCheckListRoomDatasource.listByIdCheckList(idCheckList)
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

    override suspend fun countByIdCheckList(idCheckList: Int): Result<Int> {
        val result = itemCheckListRoomDatasource.countByIdCheckList(idCheckList)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}
