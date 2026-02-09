package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.TB_FUNCTION_STOP
import br.com.usinasantafe.cmm.utils.BaseUpdateTable
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateTableFunctionStop {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableFunctionStop @Inject constructor(
    getToken: GetToken,
    functionStopRepository: FunctionStopRepository
) : BaseUpdateTable<FunctionStop>(
    getToken,
    functionStopRepository,
    TB_FUNCTION_STOP,
    getClassAndMethod()
), UpdateTableFunctionStop