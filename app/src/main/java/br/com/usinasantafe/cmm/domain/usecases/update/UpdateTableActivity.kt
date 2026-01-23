package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.lib.failure
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.updatePercentage
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
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(1f, count, sizeAll),
                tableUpdate = TB_ACTIVITY,
                levelUpdate = LevelUpdate.RECOVERY
            )
        )
        val resultGetToken = getToken()
        resultGetToken.onFailure {
            val failure = failure(getClassAndMethod(), it)
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
            return@flow
        }
        val token = resultGetToken.getOrNull()!!
        val resultRecoverAll = activityRepository.listAll(token)
        resultRecoverAll.onFailure {
            val failure = failure(getClassAndMethod(), it)
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(2f, count, sizeAll),
                tableUpdate = TB_ACTIVITY,
                levelUpdate = LevelUpdate.CLEAN
            )
        )
        val resultDeleteAll = activityRepository.deleteAll()
        resultDeleteAll.onFailure {
            val failure = failure(getClassAndMethod(), it)
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(3f, count, sizeAll),
                tableUpdate = TB_ACTIVITY,
                levelUpdate = LevelUpdate.SAVE
            )
        )
        val entityList = resultRecoverAll.getOrNull()!!
        val resultAddAll = activityRepository.addAll(entityList)
        resultAddAll.onFailure {
            val failure = failure(getClassAndMethod(), it)
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
            return@flow
        }
    }

}