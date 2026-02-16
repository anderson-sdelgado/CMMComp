package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class ITurnRepository @Inject constructor(
    private val turnRetrofitDatasource: TurnRetrofitDatasource,
    private val turnRoomDatasource: TurnRoomDatasource
): TurnRepository {

    override suspend fun addAll(list: List<Turn>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityToRoomModel() }
            turnRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            turnRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(
        token: String
    ): Result<List<Turn>> =
        call(getClassAndMethod()) {
            val modelList = turnRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<Turn>> =
        call(getClassAndMethod()) {
            val modelList = turnRoomDatasource.listByCodTurnEquip(codTurnEquip).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun getNroTurnByIdTurn(idTurn: Int): Result<Int> =
        call(getClassAndMethod()) {
            turnRoomDatasource.getNroTurnByIdTurn(idTurn).getOrThrow()
        }
}
