package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IItemMotoMecRoomDatasource @Inject constructor(
    private val itemMotoMecDao: ItemMotoMecDao
): ItemMotoMecRoomDatasource {

    override suspend fun save(itemMotoMecRoomModel: ItemMotoMecRoomModel): Result<Long> =
        result(getClassAndMethod()) {
            itemMotoMecDao.insert(itemMotoMecRoomModel)
        }

    override suspend fun hasByIdHeader(idHeader: Int): Result<Boolean> =
        result(getClassAndMethod()) {
            itemMotoMecDao.countByIdHeader(idHeader) > 0
        }

    override suspend fun listByIdHeaderAndSend(idHeader: Int): Result<List<ItemMotoMecRoomModel>> =
        result(getClassAndMethod()) {
            itemMotoMecDao.listByIdHeaderAndStatusSend(
                idHeader = idHeader,
                statusSend = StatusSend.SEND
            )
        }

    override suspend fun setSentNote(
        id: Int,
        idServ: Int
    ): EmptyResult =
        result(getClassAndMethod()) {
            val model = itemMotoMecDao.getById(id)
            model.idServ = idServ
            model.statusSend = StatusSend.SENT
            itemMotoMecDao.update(model)
        }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<ItemMotoMecRoomModel>> =
        result(getClassAndMethod()) {
            itemMotoMecDao.listByIdHeader(idHeader)
        }

    override suspend fun hasByIdStopAndIdHeader(
        idStop: Int,
        idHeader: Int
    ): Result<Boolean> =
        result(getClassAndMethod()) {
            itemMotoMecDao.countByIdStopAndIdHeader(
                idStop = idStop,
                idHeader = idHeader
            ) > 0
        }

    override suspend fun getLastByIdHeader(idHeader: Int): Result<ItemMotoMecRoomModel> =
        result(getClassAndMethod()) {
            itemMotoMecDao.getLastByIdHeader(idHeader) ?: throw Exception("Not has data")
        }

}