package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.TB_FUNCTION_ACTIVITY
import br.com.usinasantafe.cmm.utils.BaseUpdateTable
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateTableFunctionActivity {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableFunctionActivity @Inject constructor(
    getToken: GetToken,
    functionActivityRepository: FunctionActivityRepository
) : BaseUpdateTable<FunctionActivity>(
    getToken,
    functionActivityRepository,
    TB_FUNCTION_ACTIVITY,
    getClassAndMethod()
), UpdateTableFunctionActivity