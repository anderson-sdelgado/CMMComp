package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.lib.failure
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY
import br.com.usinasantafe.cmm.presenter.model.emitFailure
import br.com.usinasantafe.cmm.presenter.model.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableActivity {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableActivity @Inject constructor(
    private val getToken: GetToken,
    private val activityRepository: ActivityRepository
): UpdateTableActivity {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        return@flow runCatching {
            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_ACTIVITY)
            val token = getToken().getOrThrow()
            val entityList = activityRepository.listAll(token).getOrThrow()
            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_ACTIVITY)
            activityRepository.deleteAll().getOrThrow()
            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_ACTIVITY)
            activityRepository.addAll(entityList).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = {
                val failure = failure(getClassAndMethod(), it)
                emitFailure(failure)
            }
        )
    }

}