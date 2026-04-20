package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_OS
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.emitProgress
import br.com.usinasantafe.cmm.utils.flowCall
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableOS {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float = 1f
    ): Flow<UpdateStatusState>
}

class IUpdateTableOS @Inject constructor(
    private val getToken: GetToken,
    private val osRepository: OSRepository
): UpdateTableOS {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_OS)
            val token = getToken().getOrThrow()
            val entityList = osRepository.listAll(token).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_OS)
            osRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_OS)
            osRepository.addAll(entityList).getOrThrow()

        }
    }
}