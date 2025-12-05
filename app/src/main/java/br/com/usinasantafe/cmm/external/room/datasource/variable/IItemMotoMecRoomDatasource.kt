package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemMotoMecRoomDatasource @Inject constructor(
    private val itemMotoMecDao: ItemMotoMecDao
): ItemMotoMecRoomDatasource {

    override suspend fun save(itemMotoMecRoomModel: ItemMotoMecRoomModel): Result<Boolean> {
        try {
            itemMotoMecDao.insert(itemMotoMecRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkHasByIdHeader(idHeader: Int): Result<Boolean> {
        try {
            val result = itemMotoMecDao.countByIdHeader(idHeader) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdHeaderAndSend(idHeader: Int): Result<List<ItemMotoMecRoomModel>> {
        try {
            val result = itemMotoMecDao.listByIdHeaderAndStatusSend(
                idHeader = idHeader,
                statusSend = StatusSend.SEND
            )
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setSentNote(
        id: Int,
        idServ: Int
    ): Result<Boolean> {
        try {
            val model = itemMotoMecDao.getById(id)
            model.idServ = idServ
            model.statusSend = StatusSend.SENT
            itemMotoMecDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<ItemMotoMecRoomModel>> {
        try {
            val list = itemMotoMecDao.listByIdHeader(idHeader)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun hasByIdStopAndIdHeader(
        idStop: Int,
        idHeader: Int
    ): Result<Boolean> {
        try {
            val qtd = itemMotoMecDao.countByIdStopAndIdHeader(
                idStop = idStop,
                idHeader = idHeader
            )
            val check = qtd > 0
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getLastByIdHeader(idHeader: Int): Result<ItemMotoMecRoomModel> {
        try {
            val model = itemMotoMecDao.getLastByIdHeader(idHeader) ?: return resultFailure(
                context = getClassAndMethod(),
                cause = Exception("Not has data")
            )
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }


}