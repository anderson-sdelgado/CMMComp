package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
            result.onFailure { return Result.failure(it) }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}