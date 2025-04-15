package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.TurnoDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnoRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnoRoomModel
import javax.inject.Inject

class ITurnoRoomDatasource @Inject constructor(
    private val turnoDao: TurnoDao
) : TurnoRoomDatasource {
    override suspend fun addAll(list: List<TurnoRoomModel>): Result<Boolean> {
        try {
            turnoDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITurnoRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            turnoDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITurnoRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
