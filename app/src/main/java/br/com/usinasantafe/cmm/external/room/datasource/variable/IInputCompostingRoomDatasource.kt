package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.InputCompostingDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.InputCompostingRoomDatasource
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusComposting
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IInputCompostingRoomDatasource @Inject constructor(
    private val inputCompostingDao: InputCompostingDao
): InputCompostingRoomDatasource {
    override suspend fun hasSentLoad(): Result<Boolean> {
        try {
            val list = inputCompostingDao.listByStatusAndStatusSendAndStatusComposting(
                status = Status.OPEN,
                statusSend = StatusSend.SENT,
                statusComposting = StatusComposting.LOAD
            )
            val check = list.isNotEmpty()
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun hasWill(): Result<Boolean> {
        try {
            val list = inputCompostingDao.listByStatus(Status.OPEN)
            if (list.isEmpty()) return Result.success(false)
            val model = inputCompostingDao.getByStatus(Status.OPEN)
            val check = model.idWill != null
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}