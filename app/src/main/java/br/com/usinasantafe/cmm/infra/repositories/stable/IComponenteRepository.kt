package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Componente
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ComponenteRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ComponenteRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ComponenteRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityComponenteToRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IComponenteRepository @Inject constructor(
    private val componenteRetrofitDatasource: ComponenteRetrofitDatasource, // Injeção da datasource Retrofit
    private val componenteRoomDatasource: ComponenteRoomDatasource // Injeção da datasource Room
) : ComponenteRepository {

    override suspend fun addAll(list: List<Componente>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityComponenteToRoomModel() }
            val result = componenteRoomDatasource.addAll(roomModelList)
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
        val result = componenteRoomDatasource.deleteAll()
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
    ): Result<List<Componente>> {
        try {
            val result = componenteRetrofitDatasource.listAll(token)
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
}
