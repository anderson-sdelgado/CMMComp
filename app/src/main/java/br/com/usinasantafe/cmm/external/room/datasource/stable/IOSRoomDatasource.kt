package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.OSRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import javax.inject.Inject

class IOSRoomDatasource @Inject constructor(
    private val osDao: OSDao
) : OSRoomDatasource {

    override suspend fun addAll(list: List<OSRoomModel>): Result<Boolean> {
        try {
            osDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IOSRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            osDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IOSRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkNroOS(nroOS: Int): Result<Boolean> {
        try {
            val result = osDao.checkNroOS(nroOS) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IOSRoomDatasource.checkNroOS",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun add(model: OSRoomModel): Result<Boolean> {
        try {
            osDao.insert(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IOSRoomDatasource.add",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listByNroOS(nroOS: Int): Result<List<OSRoomModel>> {
        try {
            val list = osDao.listByNroOS(nroOS)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IOSRoomDatasource.listByNroOS",
                message = "-",
                cause = e
            )
        }
    }

}
