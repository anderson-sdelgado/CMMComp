package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_TURN
import br.com.usinasantafe.cmm.presenter.model.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableTurn {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableTurn @Inject constructor(
    private val getToken: GetToken,
    private val turnRepository: TurnRepository
): UpdateTableTurn {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_TURN)
            val token = getToken().getOrThrow()
            val entityList = turnRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_TURN)
            turnRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_TURN)
            turnRepository.addAll(entityList).getOrThrow()

        }
    }

}