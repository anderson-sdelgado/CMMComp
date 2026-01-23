package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.lib.failure
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_FUNCTION_ACTIVITY
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableFunctionActivity {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableFunctionActivity @Inject constructor(
    private val getToken: GetToken,
    private val functionActivityRepository: FunctionActivityRepository
): UpdateTableFunctionActivity {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(1f, count, sizeAll),
                tableUpdate = TB_FUNCTION_ACTIVITY,
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
                    levelUpdate = null,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        val token = resultGetToken.getOrNull()!!
        val resultRecoverAll = functionActivityRepository.listAll(token)
        resultRecoverAll.onFailure {
            val failure = failure(getClassAndMethod(), it)
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    levelUpdate = null,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(2f, count, sizeAll),
                tableUpdate = TB_FUNCTION_ACTIVITY,
                levelUpdate = LevelUpdate.CLEAN
            )
        )
        val resultDeleteAll = functionActivityRepository.deleteAll()
        resultDeleteAll.onFailure {
            val failure = failure(getClassAndMethod(), it)
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    levelUpdate = null,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(3f, count, sizeAll),
                tableUpdate = TB_FUNCTION_ACTIVITY,
                levelUpdate = LevelUpdate.SAVE
            )
        )
        val entityList = resultRecoverAll.getOrNull()!!
        val resultAddAll = functionActivityRepository.addAll(entityList)
        resultAddAll.onFailure {
            val failure = failure(getClassAndMethod(), it)
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    levelUpdate = null,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

}