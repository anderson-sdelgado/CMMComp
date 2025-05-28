package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import javax.inject.Inject

class ITurnRoomDatasource @Inject constructor(
    private val turnDao: TurnDao
) : TurnRoomDatasource {

    override suspend fun addAll(list: List<TurnRoomModel>): Result<Boolean> {
        try {
            turnDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITurnRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            turnDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITurnRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getListByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<TurnRoomModel>> {
        try {
            val list = turnDao.getListByCodTurnEquip(codTurnEquip)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITurnRoomDatasource.getListByCodTurnEquip",
                message = "-",
                cause = e
            )
        }
    }

}
