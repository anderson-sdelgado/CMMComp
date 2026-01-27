package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class ITurnRoomDatasource @Inject constructor(
    private val turnDao: TurnDao
) : TurnRoomDatasource {

    override suspend fun addAll(list: List<TurnRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            turnDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            turnDao.deleteAll()
        }

    override suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<TurnRoomModel>>  =
        result(getClassAndMethod()) {
            turnDao.listByCodTurnEquip(codTurnEquip)
        }

    override suspend fun getNroTurnByIdTurn(idTurn: Int): Result<Int> =
        result(getClassAndMethod()) {
            turnDao.getByIdTurn(idTurn).nroTurn
        }

}
