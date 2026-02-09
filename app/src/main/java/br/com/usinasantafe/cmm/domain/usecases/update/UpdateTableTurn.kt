package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_STOP
import br.com.usinasantafe.cmm.lib.TB_TURN
import br.com.usinasantafe.cmm.utils.BaseUpdateTable
import br.com.usinasantafe.cmm.utils.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableTurn {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableTurn @Inject constructor(
    getToken: GetToken,
    turnRepository: TurnRepository
) : BaseUpdateTable<Turn>(
    getToken,
    turnRepository,
    TB_STOP,
    getClassAndMethod()
), UpdateTableTurn