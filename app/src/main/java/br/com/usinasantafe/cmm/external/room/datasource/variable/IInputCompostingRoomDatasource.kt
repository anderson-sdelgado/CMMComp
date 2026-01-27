package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.InputCompostingDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.InputCompostingRoomDatasource
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusComposting
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IInputCompostingRoomDatasource @Inject constructor(
    private val inputCompostingDao: InputCompostingDao
): InputCompostingRoomDatasource {
    override suspend fun hasSentLoad(): Result<Boolean> =
        result(getClassAndMethod()) {
            inputCompostingDao.listByStatusAndStatusSendAndStatusComposting(
                status = Status.OPEN,
                statusSend = StatusSend.SENT,
                statusComposting = StatusComposting.LOAD
            ).isNotEmpty()
        }

    override suspend fun hasWill(): Result<Boolean> =
        result(getClassAndMethod()) {
            val list = inputCompostingDao.listByStatus(Status.OPEN)
            if (list.isEmpty()) return@result false
            val model = inputCompostingDao.getByStatus(Status.OPEN)
            model.idWill != null
        }
}