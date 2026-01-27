package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IHeaderCheckListRoomDatasource @Inject constructor(
    private val headerCheckListDao: HeaderCheckListDao
): HeaderCheckListRoomDatasource {

    override suspend fun save(headerCheckListRoomModel: HeaderCheckListRoomModel): Result<Long> =
        result(getClassAndMethod()) {
            headerCheckListDao.insert(headerCheckListRoomModel)
        }

    override suspend fun hasSend(): Result<Boolean> =
        result(getClassAndMethod()) {
            headerCheckListDao.countByStatusSend(StatusSend.SEND) > 0
        }

    override suspend fun listBySend(): Result<List<HeaderCheckListRoomModel>> =
        result(getClassAndMethod()) {
            headerCheckListDao.listByStatusSend(StatusSend.SEND)
        }

    override suspend fun setSentAndIdServById(
        id: Int,
        idServ: Int
    ): EmptyResult =
        result(getClassAndMethod()) {
            val model = headerCheckListDao.getById(id)
            model.idServ = idServ
            model.statusSend = StatusSend.SENT
            headerCheckListDao.update(model)
        }

}