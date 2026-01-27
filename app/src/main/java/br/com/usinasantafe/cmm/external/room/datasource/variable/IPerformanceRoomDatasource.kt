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

}