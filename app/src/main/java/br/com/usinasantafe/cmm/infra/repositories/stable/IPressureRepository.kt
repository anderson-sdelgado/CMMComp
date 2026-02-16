package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.domain.repositories.stable.PressureRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.PressureRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.PressureRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject
import kotlin.getOrThrow

class IPressureRepository @Inject constructor(
    private val pressureRetrofitDatasource: PressureRetrofitDatasource,
    private val pressureRoomDatasource: PressureRoomDatasource
): PressureRepository {

    override suspend fun addAll(list: List<Pressure>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            pressureRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            pressureRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<Pressure>> =
        call(getClassAndMethod()) {
            val modelList = pressureRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByIdNozzle(id: Int): Result<List<Pressure>> {
        TODO("Not yet implemented")
    }

}