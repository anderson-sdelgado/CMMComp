package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.MotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.MotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.MotoMecRoomModel
import javax.inject.Inject

class IMotoMecRoomDatasource @Inject constructor(
    private val motoMecDao: MotoMecDao
) : MotoMecRoomDatasource {
    override suspend fun addAll(list: List<MotoMecRoomModel>): Result<Boolean> {
        try {
            motoMecDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMotoMecRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            motoMecDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMotoMecRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
