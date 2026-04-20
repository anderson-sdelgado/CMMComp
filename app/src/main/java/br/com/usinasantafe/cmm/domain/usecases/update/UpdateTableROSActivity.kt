package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_R_OS_ACTIVITY
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.emitProgress
import br.com.usinasantafe.cmm.utils.flowCall
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableROSActivity {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float = 1f
    ): Flow<UpdateStatusState>
}

class IUpdateTableROSActivity @Inject constructor(
    private val getToken: GetToken,
    private val rOSActivityRepository: ROSActivityRepository
): UpdateTableROSActivity {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_R_OS_ACTIVITY)
            val token = getToken().getOrThrow()
            val entityList = rOSActivityRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_R_OS_ACTIVITY)
            rOSActivityRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_R_OS_ACTIVITY)
            rOSActivityRepository.addAll(entityList).getOrThrow()

        }
    }

}