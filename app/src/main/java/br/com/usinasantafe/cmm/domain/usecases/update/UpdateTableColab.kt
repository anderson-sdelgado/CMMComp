package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_COLAB
import br.com.usinasantafe.cmm.presenter.model.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableColab {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableColab @Inject constructor(
    private val getToken: GetToken,
    private val colabRepository: ColabRepository
): UpdateTableColab {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_COLAB)
            val token = getToken().getOrThrow()
            val entityList = colabRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_COLAB)
            colabRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_COLAB)
            colabRepository.addAll(entityList).getOrThrow()

        }
    }

}