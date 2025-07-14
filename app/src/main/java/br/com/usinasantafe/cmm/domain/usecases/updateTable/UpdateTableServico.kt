package br.com.usinasantafe.cmm.domain.usecases.updateTable

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.ServicoRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TB_SERVICO
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableServico {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableServico @Inject constructor(
    private val getToken: GetToken,
    private val servicoRepository: ServicoRepository
): UpdateTableServico {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
//        emit(
//            ResultUpdateModel(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela $TB_SERVICO do Web Service",
//                currentProgress = updatePercentage(1f, count, sizeAll)
//            )
//        )
//        val resultGetToken = getToken()
//        if (resultGetToken.isFailure) {
//            val error = resultGetToken.exceptionOrNull()!!
//            val failure =
//                "UpdateTableServico -> ${error.message} -> ${error.cause.toString()}"
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
//        val resultRecoverAll = servicoRepository.recoverAll(token)
//        if (resultRecoverAll.isFailure) {
//            val error = resultRecoverAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableServico -> ${error.message} -> ${error.cause.toString()}"
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
//                msgProgress = "Limpando a tabela $TB_SERVICO",
//                currentProgress = updatePercentage(2f, count, sizeAll)
//            )
//        )
//        val resultDeleteAll = servicoRepository.deleteAll()
//        if (resultDeleteAll.isFailure) {
//            val error = resultDeleteAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableServico -> ${error.message} -> ${error.cause.toString()}"
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
//                msgProgress = "Salvando dados na tabela $TB_SERVICO",
//                currentProgress = updatePercentage(3f, count, sizeAll)
//            )
//        )
//        val entityList = resultRecoverAll.getOrNull()!!
//        val resultAddAll = servicoRepository.addAll(entityList)
//        if (resultAddAll.isFailure) {
//            val error = resultAddAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableServico -> ${error.message} -> ${error.cause.toString()}"
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