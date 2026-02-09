package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY
import br.com.usinasantafe.cmm.utils.BaseUpdateTable
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateTableActivity {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableActivity @Inject constructor(
    getToken: GetToken,
    activityRepository: ActivityRepository
) : BaseUpdateTable<Activity>(
    getToken,
    activityRepository,
    TB_ACTIVITY,
    getClassAndMethod()
), UpdateTableActivity