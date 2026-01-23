package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
                context = getClassAndMethod(),
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
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<TurnRoomModel>> {
        try {
            val list = turnDao.listByCodTurnEquip(codTurnEquip)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getNroTurnByIdTurn(idTurn: Int): Result<Int> {
        try {
            val model = turnDao.getByIdTurn(idTurn)
            return Result.success(model.nroTurn)
            } catch (e: Exception) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = e
                )
        }
    }

}
