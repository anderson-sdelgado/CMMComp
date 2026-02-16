package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.domain.repositories.stable.NozzleRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.NozzleRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.NozzleRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject
import kotlin.getOrThrow

class INozzleRepository  @Inject constructor(
    private val nozzleRetrofitDatasource: NozzleRetrofitDatasource,
    private val nozzleRoomDatasource: NozzleRoomDatasource
): NozzleRepository {

    override suspend fun addAll(list: List<Nozzle>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            nozzleRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            nozzleRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<Nozzle>>  =
        call(getClassAndMethod()) {
            val modelList = nozzleRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

}