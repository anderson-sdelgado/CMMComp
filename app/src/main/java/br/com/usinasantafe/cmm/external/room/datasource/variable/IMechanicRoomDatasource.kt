package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.MechanicDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class IMechanicRoomDatasource @Inject constructor(
    private val mechanicDao: MechanicDao
): MechanicRoomDatasource {

    override suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean> {
        try {
            val count = mechanicDao.countOpenByIdHeader(idHeader)
            return Result.success(count > 0)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setFinishNote(): Result<Boolean> {
        try {
            val model = mechanicDao.getByOpen()
            model.dateHourFinish = Date()
            model.status = Status.FINISH
            mechanicDao.update(model)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}