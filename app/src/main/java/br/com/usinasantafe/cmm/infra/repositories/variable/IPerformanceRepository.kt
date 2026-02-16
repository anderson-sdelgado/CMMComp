package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Performance
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.PerformanceRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject
import kotlin.getOrThrow

class IPerformanceRepository @Inject constructor(
    private val performanceRoomDatasource: PerformanceRoomDatasource,
): PerformanceRepository {

    override suspend fun initial(nroOS: Int, idHeader: Int): EmptyResult =
        call(getClassAndMethod()) {
            performanceRoomDatasource.insert(
                PerformanceRoomModel(
                    nroOS = nroOS,
                    idHeader = idHeader,
                )
            ).getOrThrow()
        }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<Performance>> =
        call(getClassAndMethod()) {
            performanceRoomDatasource.listByIdHeader(idHeader).getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun update(
        id: Int,
        value: Double
    ): EmptyResult =
        call(getClassAndMethod()) {
            performanceRoomDatasource.update(id, value).getOrThrow()
        }

    override suspend fun getNroOSById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            performanceRoomDatasource.getNroOSById(id).getOrThrow()
        }

    override suspend fun hasByIdHeaderAndValueNull(idHeader: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            performanceRoomDatasource.hasByIdHeaderAndValueNull(idHeader).getOrThrow()
        }

    override suspend fun hasByIdHeader(idHeader: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            performanceRoomDatasource.hasByIdHeader(idHeader).getOrThrow()
        }


}