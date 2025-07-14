package br.com.usinasantafe.cmm.domain.usecases.updateTable

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TB_MOTOMEC
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableMotoMec {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableMotoMec @Inject constructor(
    private val getToken: GetToken,
    private val motoMecRepository: MotoMecRepository
): UpdateTableMotoMec {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
//        emit(
//            ResultUpdateModel(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela $TB_MOTOMEC do Web Service",
//                currentProgress = updatePercentage(1f, count, sizeAll)
//            )
//        )
//        val resultGetToken = getToken()
//        if (resultGetToken.isFailure) {
//            val error = resultGetToken.exceptionOrNull()!!
//            val failure =
//                "UpdateTableMotoMec -> ${error.message} -> ${error.cause.toString()}"
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
//        val resultRecoverAll = motoMecRepository.recoverAll(token)
//        if (resultRecoverAll.isFailure) {
//            val error = resultRecoverAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableMotoMec -> ${error.message} -> ${error.cause.toString()}"
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
//                msgProgress = "Limpando a tabela $TB_MOTOMEC",
//                currentProgress = updatePercentage(2f, count, sizeAll)
//            )
//        )
//        val resultDeleteAll = motoMecRepository.deleteAll()
//        if (resultDeleteAll.isFailure) {
//            val error = resultDeleteAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableMotoMec -> ${error.message} -> ${error.cause.toString()}"
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
//                msgProgress = "Salvando dados na tabela $TB_MOTOMEC",
//                currentProgress = updatePercentage(3f, count, sizeAll)
//            )
//        )
//        val entityList = resultRecoverAll.getOrNull()!!
//        val resultAddAll = motoMecRepository.addAll(entityList)
//        if (resultAddAll.isFailure) {
//            val error = resultAddAll.exceptionOrNull()!!
//            val failure =
//                "UpdateTableMotoMec -> ${error.message} -> ${error.cause.toString()}"
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