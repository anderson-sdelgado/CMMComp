package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.PressureDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.PressureRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.PressureRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IPressureRoomDatasource @Inject constructor(
    private val pressureDao: PressureDao
): PressureRoomDatasource {

    override suspend fun addAll(list: List<PressureRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            pressureDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            pressureDao.deleteAll()
        }

    override suspend fun listByIdNozzle(idNozzle: Int): Result<List<PressureRoomModel>> =
        result(getClassAndMethod()) {
            pressureDao.listByIdNozzle(idNozzle)
        }

    override suspend fun listByIdNozzleAndValuePressure(
        idNozzle: Int,
        valuePressure: Double
    ): Result<List<PressureRoomModel>> =
        result(getClassAndMethod()) {
            pressureDao.listByIdNozzleAndValuePressure(idNozzle, valuePressure)
        }

}