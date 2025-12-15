package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckTypeTranshipmentInActivity {
    suspend operator fun invoke(idActivity: Int): Result<Boolean>
}

class ICheckTypeTranshipmentInActivity @Inject constructor(
    private val functionActivityRepository: FunctionActivityRepository
): CheckTypeTranshipmentInActivity {

    override suspend fun invoke(idActivity: Int): Result<Boolean> {
        val resultListFunctionActivity =
            functionActivityRepository.listByIdActivity(idActivity)
        resultListFunctionActivity.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val functionActivityList = resultListFunctionActivity.getOrNull()!!
        val ret = functionActivityList.any { it.typeActivity == TypeActivity.TRANSHIPMENT }
        return Result.success(ret)
    }

}