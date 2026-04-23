package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.OSRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
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
            val modelList = list.map { it.entityToRoomModel() }
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

    override suspend fun hasByNro(nro: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            osRoomDatasource.hasByNro(nro).getOrThrow()
        }

    override suspend fun listByNro(
        token: String,
        nro: Int
    ): Result<List<OS>> =
        call(getClassAndMethod()) {
            val modelList = osRetrofitDatasource.listByNro(token, nro).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun add(os: OS): EmptyResult =
        call(getClassAndMethod()) {
            osRoomDatasource.add(os.entityToRoomModel()).getOrThrow()
        }

    override suspend fun getByNro(nro: Int): Result<OS> =
        call(getClassAndMethod()) {
            osRoomDatasource.getByNro(nro).getOrThrow().roomModelToEntity()
        }

    override suspend fun hasByNroAndIdRelease(
        nroOS: Int,
        idRelease: Int
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            osRoomDatasource.hasByNroAndIdRelease(nroOS, idRelease).getOrThrow()
        }

}
