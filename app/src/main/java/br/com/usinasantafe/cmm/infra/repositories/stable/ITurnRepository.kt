package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.BaseStableRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class ITurnRepository @Inject constructor(
    private val turnRetrofitDatasource: TurnRetrofitDatasource,
    private val turnRoomDatasource: TurnRoomDatasource
) : BaseStableRepository<
        Turn,
        TurnRetrofitModel,
        TurnRoomModel
        >(
    retrofitListAll = turnRetrofitDatasource::listAll,
    roomAddAll = turnRoomDatasource::addAll,
    roomDeleteAll = turnRoomDatasource::deleteAll,
    retrofitToEntity = TurnRetrofitModel::retrofitModelToEntity,
    entityToRoom = Turn::entityToRoomModel,
    classAndMethod = getClassAndMethod()
),
    TurnRepository {

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
