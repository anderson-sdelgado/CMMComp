package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckRegOperator {
    suspend operator fun invoke(regOperator: String): Result<Boolean>
}

class ICheckRegOperator @Inject constructor(
    private val colabRepository: ColabRepository
): CheckRegOperator {

    override suspend fun invoke(regOperator: String): Result<Boolean> {
        try {
            val result = colabRepository.checkByReg(
                regOperator.toInt()
            )
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}