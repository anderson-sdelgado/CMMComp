package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnoRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TB_TURNO
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableTurno {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdate>
}

class IUpdateTableTurno @Inject constructor(
    private val getToken: GetToken,
    private val turnoRepository: TurnoRepository
): UpdateTableTurno {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdate> = flow {
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_TURNO do Web Service",
                currentProgress = updatePercentage(1f, count, sizeAll)
            )
        )
        val resultGetToken = getToken()
        if (resultGetToken.isFailure) {
            val error = resultGetToken.exceptionOrNull()!!
            val failure =
                "UpdateTableTurno -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        val token = resultGetToken.getOrNull()!!
        val resultRecoverAll = turnoRepository.recoverAll(token)
        if (resultRecoverAll.isFailure) {
            val error = resultRecoverAll.exceptionOrNull()!!
            val failure =
                "UpdateTableTurno -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_TURNO",
                currentProgress = updatePercentage(2f, count, sizeAll)
            )
        )
        val resultDeleteAll = turnoRepository.deleteAll()
        if (resultDeleteAll.isFailure) {
            val error = resultDeleteAll.exceptionOrNull()!!
            val failure =
                "UpdateTableTurno -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_TURNO",
                currentProgress = updatePercentage(3f, count, sizeAll)
            )
        )
        val entityList = resultRecoverAll.getOrNull()!!
        val resultAddAll = turnoRepository.addAll(entityList)
        if (resultAddAll.isFailure) {
            val error = resultAddAll.exceptionOrNull()!!
            val failure =
                "UpdateTableTurno -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

}