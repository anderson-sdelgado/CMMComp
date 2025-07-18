package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMecan
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMecanRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemOSMecanRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemOSMecanRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemOSMecanRepository @Inject constructor(
    private val itemOSMecanRetrofitDatasource: ItemOSMecanRetrofitDatasource, // Injeção da datasource Retrofit
    private val itemOSMecanRoomDatasource: ItemOSMecanRoomDatasource // Injeção da datasource Room
) : ItemOSMecanRepository {

    override suspend fun addAll(list: List<ItemOSMecan>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = itemOSMecanRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = itemOSMecanRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<ItemOSMecan>> {
        try {
            val result = itemOSMecanRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
