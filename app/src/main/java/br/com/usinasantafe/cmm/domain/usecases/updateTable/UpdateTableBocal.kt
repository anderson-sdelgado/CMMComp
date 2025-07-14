package br.com.usinasantafe.cmm.domain.usecases.updateTable

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.BocalRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TB_BOCAL
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableBocal {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableBocal @Inject constructor(
    private val getToken: GetToken,
    private val bocalRepository: BocalRepository
): UpdateTableBocal {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
//        emit(
//            ResultUpdateModel(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela $TB_BOCAL do Web Service",
//                currentProgress = updatePercentage(1f, count, sizeAll)
//            )
//        )
//        val resultGetToken = getToken()
//        if (resultGetToken.isFailure) {
//            val error = resultGetToken.exceptionOrNull()!!
//            val failure =
//                "UpdateTableBocal -> ${error.message} -> ${error.cause.toString()}"
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
//        val resultRecoverAll = bocalRepository.recoverAll(token)
//        if (resultRecoverAll.isFailure) {
//            val error = resultRecoverAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableBocal -> ${error.message} -> ${error.cause.toString()}"
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
//                msgProgress = "Limpando a tabela $TB_BOCAL",
//                currentProgress = updatePercentage(2f, count, sizeAll)
//            )
//        )
//        val resultDeleteAll = bocalRepository.deleteAll()
//        if (resultDeleteAll.isFailure) {
//            val error = resultDeleteAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableBocal -> ${error.message} -> ${error.cause.toString()}"
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
//                msgProgress = "Salvando dados na tabela $TB_BOCAL",
//                currentProgress = updatePercentage(3f, count, sizeAll)
//            )
//        )
//        val entityList = resultRecoverAll.getOrNull()!!
//        val resultAddAll = bocalRepository.addAll(entityList)
//        if (resultAddAll.isFailure) {
//            val error = resultAddAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableBocal -> ${error.message} -> ${error.cause.toString()}"
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