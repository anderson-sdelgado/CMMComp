package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.OSRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityOSToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IOSRepository @Inject constructor(
    private val osRetrofitDatasource: OSRetrofitDatasource,
    private val osRoomDatasource: OSRoomDatasource
) : OSRepository {

    override suspend fun addAll(list: List<OS>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityOSToRoomModel() }
            osRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            osRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(
        token: String
    ): Result<List<OS>> =
        call(getClassAndMethod()) {
            val modelList = osRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun hasByNroOS(nroOS: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            osRoomDatasource.hasByNroOS(nroOS).getOrThrow()
        }

    override suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<OS>> =
        call(getClassAndMethod()) {
            val modelList = osRetrofitDatasource.getListByNroOS(token, nroOS).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun add(os: OS): EmptyResult =
        call(getClassAndMethod()) {
            osRoomDatasource.add(os.entityOSToRoomModel()).getOrThrow()
        }

    override suspend fun getByNroOS(nroOS: Int): Result<OS> =
        call(getClassAndMethod()) {
            osRoomDatasource.getByNroOS(nroOS).getOrThrow().roomModelToEntity()
        }

}
