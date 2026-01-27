package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.MechanicDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import java.util.Date
import javax.inject.Inject

class IMechanicRoomDatasource @Inject constructor(
    private val mechanicDao: MechanicDao
): MechanicRoomDatasource {

    override suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean> =
        result(getClassAndMethod()) {
            mechanicDao.countOpenByIdHeader(idHeader) > 0
        }

    override suspend fun setFinishNote(): EmptyResult =
        result(getClassAndMethod()) {
            val model = mechanicDao.getByOpen()
            model.dateHourFinish = Date()
            model.status = Status.FINISH
            mechanicDao.update(model)
        }

}