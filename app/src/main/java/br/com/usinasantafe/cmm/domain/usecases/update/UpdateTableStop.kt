package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_STOP
import br.com.usinasantafe.cmm.utils.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableStop {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableStop @Inject constructor(
    private val getToken: GetToken,
    private val stopRepository: StopRepository
): UpdateTableStop {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_STOP)
            val token = getToken().getOrThrow()
            val entityList = stopRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_STOP)
            stopRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_STOP)
            stopRepository.addAll(entityList).getOrThrow()

        }
    }

}