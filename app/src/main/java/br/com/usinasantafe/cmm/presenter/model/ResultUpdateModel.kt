package br.com.usinasantafe.cmm.presenter.model

import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.FlowCollector

data class ResultUpdateModel(
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val failure: String = "",
    val flagProgress: Boolean = true,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

suspend fun FlowCollector<ResultUpdateModel>.emitProgress(
    count: Float,
    sizeAll: Float,
    level: LevelUpdate,
    table: String,
    flagProgress: Boolean = true
) {
    val step = when(level){
        LevelUpdate.RECOVERY -> 1f
        LevelUpdate.CLEAN -> 2f
        LevelUpdate.SAVE -> 3f
        else -> 0f
    }
    emit(
        ResultUpdateModel(
            flagProgress = flagProgress,
            currentProgress = updatePercentage(step, count, sizeAll),
            tableUpdate = table,
            levelUpdate = level
        )
    )
}

suspend fun FlowCollector<ResultUpdateModel>.emitFailure(
    failure: String,
) {
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
}