package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasRegColab {
    suspend operator fun invoke(regOperator: String): Result<Boolean>
}

class IHasRegColab @Inject constructor(
    private val colabRepository: ColabRepository
): HasRegColab {

    override suspend fun invoke(regOperator: String): Result<Boolean> {
        try {
            val result = colabRepository.hasByReg(
                regOperator.toInt()
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}