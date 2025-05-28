package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ParadaDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ParadaRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ParadaRoomModel
import javax.inject.Inject

class IParadaRoomDatasource @Inject constructor(
    private val paradaDao: ParadaDao
) : ParadaRoomDatasource {
    override suspend fun addAll(list: List<ParadaRoomModel>): Result<Boolean> {
        try {
            paradaDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IParadaRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            paradaDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IParadaRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
