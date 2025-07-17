package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemCheckListRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemCheckListRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import javax.inject.Inject

class IItemCheckListRepository @Inject constructor(
    private val itemCheckListRetrofitDatasource: ItemCheckListRetrofitDatasource,
    private val itemCheckListRoomDatasource: ItemCheckListRoomDatasource
) : ItemCheckListRepository {

    override suspend fun addAll(list: List<ItemCheckList>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = itemCheckListRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IItemCheckListRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IItemCheckListRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = itemCheckListRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IItemCheckListRepository.deleteAll",
                message = e.message,
                cause = e.cause
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
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IItemCheckListRepository.listByNroEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IItemCheckListRepository.listByNroEquip",
                message = "-",
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
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IItemCheckListRepository.checkUpdateByNroEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.qtd > 0)
        } catch (e: Exception) {
            return resultFailure(
                context = "IItemCheckListRepository.checkUpdateByNroEquip",
                message = "-",
                cause = e
            )
        }

    }

    override suspend fun listByIdCheckList(idCheckList: Int): Result<List<ItemCheckList>> {
        TODO("Not yet implemented")
    }

}
