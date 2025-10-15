package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.ComponenteRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableComponente {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableComponente @Inject constructor(
    private val getToken: GetToken,
    private val componenteRepository: ComponenteRepository
): UpdateTableComponente {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
//        emit(
//            ResultUpdateModel(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela $TB_COMPONENTE do Web Service",
//                currentProgress = updatePercentage(1f, count, sizeAll)
//            )
//        )
//        val resultGetToken = getToken()
//        if (resultGetToken.isFailure) {
//            val error = resultGetToken.exceptionOrNull()!!
//            val failure =
//                "UpdateTableComponente -> ${error.message} -> ${error.cause.toString()}"
//            emit(
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = failure,
//                    msgProgress = failure,
//                    currentProgress = 1f,
//                )
//            )
//            return@flow
//        }
//        val token = resultGetToken.getOrNull()!!
//        val resultRecoverAll = componenteRepository.recoverAll(token)
//        if (resultRecoverAll.isFailure) {
//            val error = resultRecoverAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableComponente -> ${error.message} -> ${error.cause.toString()}"
//            emit(
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = failure,
//                    msgProgress = failure,
//                    currentProgress = 1f,
//                )
//            )
//            return@flow
//        }
//        emit(
//            ResultUpdateModel(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela $TB_COMPONENTE",
//                currentProgress = updatePercentage(2f, count, sizeAll)
//            )
//        )
//        val resultDeleteAll = componenteRepository.deleteAll()
//        if (resultDeleteAll.isFailure) {
//            val error = resultDeleteAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableComponente -> ${error.message} -> ${error.cause.toString()}"
//            emit(
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = failure,
//                    msgProgress = failure,
//                    currentProgress = 1f,
//                )
//            )
//            return@flow
//        }
//        emit(
//            ResultUpdateModel(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela $TB_COMPONENTE",
//                currentProgress = updatePercentage(3f, count, sizeAll)
//            )
//        )
//        val entityList = resultRecoverAll.getOrNull()!!
//        val resultAddAll = componenteRepository.addAll(entityList)
//        if (resultAddAll.isFailure) {
//            val error = resultAddAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableComponente -> ${error.message} -> ${error.cause.toString()}"
//            emit(
//                ResultUpdateModel(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = failure,
//                    msgProgress = failure,
//                    currentProgress = 1f,
//                )
//            )
//            return@flow
//        }
    }

}