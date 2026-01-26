package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.lib.failure
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY
import br.com.usinasantafe.cmm.lib.TB_STOP
import br.com.usinasantafe.cmm.presenter.model.emitFailure
import br.com.usinasantafe.cmm.presenter.model.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.onFailure

interface UpdateTableStop {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableStop @Inject constructor(
    private val getToken: GetToken,
    private val stopRepository: StopRepository
): UpdateTableStop {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        return@flow runCatching {
            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_STOP)
            val token = getToken().getOrThrow()
            val entityList = stopRepository.listAll(token).getOrThrow()
            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_STOP)
            stopRepository.deleteAll().getOrThrow()
            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_STOP)
            stopRepository.addAll(entityList).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = {
                val failure = failure(getClassAndMethod(), it)
                emitFailure(failure)
            }
        )
    }

}