package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.StopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IStopRoomDatasource @Inject constructor(
    private val stopDao: StopDao
) : StopRoomDatasource {
    override suspend fun addAll(list: List<StopRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            stopDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            stopDao.deleteAll()
        }

    override suspend fun listByIdList(idList: List<Int>): Result<List<StopRoomModel>> =
        result(getClassAndMethod()) {
            stopDao.listByIdList(idList)
        }

    override suspend fun getById(id: Int): Result<StopRoomModel> =
        result(getClassAndMethod()) {
            stopDao.getById(id)
        }
}
