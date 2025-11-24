package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.errors.failure
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.TB_ITEM_CHECK_LIST
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.onFailure

interface UpdateTableItemCheckListByNroEquip {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableItemCheckListByNroEquip @Inject constructor(
    private val getToken: GetToken,
    private val itemCheckListRepository: ItemCheckListRepository,
    private val configRepository: ConfigRepository
): UpdateTableItemCheckListByNroEquip {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(1f, count, sizeAll),
                tableUpdate = TB_ITEM_CHECK_LIST,
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
        val resultGetNroEquip = configRepository.getNroEquip()
        resultGetNroEquip.onFailure {
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
        val nroEquip = resultGetNroEquip.getOrNull()!!
        val resultRecoverAll = itemCheckListRepository.listByNroEquip(
            token = token,
            nroEquip = nroEquip
        )
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
                tableUpdate = TB_ITEM_CHECK_LIST,
                levelUpdate = LevelUpdate.CLEAN
            )
        )
        val resultDeleteAll = itemCheckListRepository.deleteAll()
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
                tableUpdate = TB_ITEM_CHECK_LIST,
                levelUpdate = LevelUpdate.SAVE
            )
        )
        val entityList = resultRecoverAll.getOrNull()!!
        val resultAddAll = itemCheckListRepository.addAll(entityList)
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