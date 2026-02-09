package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY
import br.com.usinasantafe.cmm.lib.TB_R_ITEM_MENU_STOP
import br.com.usinasantafe.cmm.utils.BaseUpdateTable
import br.com.usinasantafe.cmm.utils.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableRItemMenuStop {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableRItemMenuStop @Inject constructor(
    getToken: GetToken,
    rItemMenuStopRepository: RItemMenuStopRepository
) : BaseUpdateTable<RItemMenuStop>(
    getToken,
    rItemMenuStopRepository,
    TB_R_ITEM_MENU_STOP,
    getClassAndMethod()
), UpdateTableRItemMenuStop
