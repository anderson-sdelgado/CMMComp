package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.PerformanceRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IPerformanceRoomDatasource @Inject constructor(
    private val performanceDao: PerformanceDao
): PerformanceRoomDatasource {

    override suspend fun insert(model: PerformanceRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            val resultHas = performanceDao.hasByIdHeaderAndNroOS(
                idHeader = model.idHeader,
                nroOS = model.nroOS
            )
            if (!resultHas) performanceDao.insert(model)
        }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<PerformanceRoomModel>> =
        result(getClassAndMethod()) {
            performanceDao.listByIdHeader(idHeader)
        }

    override suspend fun update(
        id: Int,
        value: Double
    ): EmptyResult =
        result(getClassAndMethod()){
            val model = performanceDao.getById(id)
            model.value = value
            performanceDao.update(model)
        }

    override suspend fun getNroOSById(id: Int): Result<Int> =
        result(getClassAndMethod()){
            performanceDao.getById(id).nroOS
        }

    override suspend fun hasByIdHeaderAndValueNull(idHeader: Int): Result<Boolean> =
        result(getClassAndMethod()){
            performanceDao.hasByIdHeaderAndValueNull(idHeader)
        }

    override suspend fun hasByIdHeader(idHeader: Int): Result<Boolean> =
        result(getClassAndMethod()){
            performanceDao.hasByIdHeader(idHeader)
        }

}