package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IHeaderCheckListRoomDatasource @Inject constructor(
    private val headerCheckListDao: HeaderCheckListDao
): HeaderCheckListRoomDatasource {

    override suspend fun save(headerCheckListRoomModel: HeaderCheckListRoomModel): Result<Long> {
        try {
            val id = headerCheckListDao.insert(headerCheckListRoomModel)
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun hasSend(): Result<Boolean> {
        try {
            val check = headerCheckListDao.countByStatusSend(StatusSend.SEND) > 0
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listBySend(): Result<List<HeaderCheckListRoomModel>> {
        try {
            val list = headerCheckListDao.listByStatusSend(StatusSend.SEND)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setSentAndIdServById(
        id: Int,
        idServ: Int
    ): Result<Boolean> {
        try {
            val model = headerCheckListDao.getById(id)
            model.idServ = idServ
            model.statusSend = StatusSend.SENT
            headerCheckListDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}