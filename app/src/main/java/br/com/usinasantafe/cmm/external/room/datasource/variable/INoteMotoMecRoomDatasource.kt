package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class INoteMotoMecRoomDatasource @Inject constructor(
    private val noteMotoMecDao: NoteMotoMecDao
): NoteMotoMecRoomDatasource {

    override suspend fun save(noteMotoMecRoomModel: NoteMotoMecRoomModel): Result<Boolean> {
        try {
            noteMotoMecDao.insert(noteMotoMecRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkHasByIdHeader(idHeader: Int): Result<Boolean> {
        try {
            val result = noteMotoMecDao.listByIdHeader(idHeader).isNotEmpty()
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdHeaderAndSend(idHeader: Int): Result<List<NoteMotoMecRoomModel>> {
        try {
            val result = noteMotoMecDao.listByIdHeaderAndStatusSend(
                idHeader = idHeader,
                statusSend = StatusSend.SEND
            )
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setSentNote(
        id: Int,
        idBD: Long
    ): Result<Boolean> {
        try {
            val model = noteMotoMecDao.get(id)
            model.idBD = idBD
            model.statusSend = StatusSend.SENT
            noteMotoMecDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}