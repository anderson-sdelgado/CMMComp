package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
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
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckRegOperator",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckRegOperator",
                message = "-",
                cause = e
            )
        }
    }

}