package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMechanicDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMechanicRoomDatasource
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class INoteMechanicRoomDatasource @Inject constructor(
    private val noteMechanicDao: NoteMechanicDao
): NoteMechanicRoomDatasource {

    override suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean> {
        try {
            val count = noteMechanicDao.countOpenByIdHeader(idHeader)
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
            val model = noteMechanicDao.getByOpen()
            model.dateHourFinish = Date()
            model.status = Status.FINISH
            noteMechanicDao.update(model)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}