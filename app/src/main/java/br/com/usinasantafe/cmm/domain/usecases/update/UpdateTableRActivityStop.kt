package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_R_ACTIVITY_STOP
import br.com.usinasantafe.cmm.utils.BaseUpdateTable
import br.com.usinasantafe.cmm.utils.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableRActivityStop {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableRActivityStop @Inject constructor(
    getToken: GetToken,
    rActivityStopRepository: RActivityStopRepository
):  BaseUpdateTable<RActivityStop>(
    getToken,
    rActivityStopRepository,
    TB_R_ACTIVITY_STOP,
    getClassAndMethod()
), UpdateTableRActivityStop
