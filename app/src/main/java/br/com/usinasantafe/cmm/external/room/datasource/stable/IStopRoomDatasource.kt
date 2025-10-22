package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.StopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IStopRoomDatasource @Inject constructor(
    private val stopDao: StopDao
) : StopRoomDatasource {
    override suspend fun addAll(list: List<StopRoomModel>): Result<Boolean> {
        try {
            stopDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            stopDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdList(idList: List<Int>): Result<List<StopRoomModel>> {
        try {
            val list = stopDao.listByIdList(idList)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getById(id: Int): Result<StopRoomModel> {
        try {
            val model = stopDao.getById(id)
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
