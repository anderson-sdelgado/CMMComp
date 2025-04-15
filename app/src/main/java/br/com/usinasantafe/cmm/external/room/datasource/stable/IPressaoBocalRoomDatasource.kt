package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.PressaoBocalDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.PressaoBocalRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.PressaoBocalRoomModel
import javax.inject.Inject

class IPressaoBocalRoomDatasource @Inject constructor(
    private val pressaoBocalDao: PressaoBocalDao
) : PressaoBocalRoomDatasource {
    override suspend fun addAll(list: List<PressaoBocalRoomModel>): Result<Boolean> {
        try {
            pressaoBocalDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IPressaoBocalRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            pressaoBocalDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IPressaoBocalRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
