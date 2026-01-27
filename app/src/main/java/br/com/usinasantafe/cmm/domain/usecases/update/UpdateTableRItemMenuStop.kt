package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_R_ITEM_MENU_STOP
import br.com.usinasantafe.cmm.presenter.model.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableRItemMenuStop {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableRItemMenuStop @Inject constructor(
    private val getToken: GetToken,
    private val rItemMenuStopRepository: RItemMenuStopRepository
): UpdateTableRItemMenuStop {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_R_ITEM_MENU_STOP)
            val token = getToken().getOrThrow()
            val entityList = rItemMenuStopRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_R_ITEM_MENU_STOP)
            rItemMenuStopRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_R_ITEM_MENU_STOP)
            rItemMenuStopRepository.addAll(entityList).getOrThrow()

        }
    }

}