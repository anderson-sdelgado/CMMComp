package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface HasRegColab {
    suspend operator fun invoke(regOperator: String): Result<Boolean>
}

class IHasRegColab @Inject constructor(
    private val colabRepository: ColabRepository
): HasRegColab {

    override suspend fun invoke(regOperator: String): Result<Boolean> {
        return runCatching {
            val regOperatorInt = runCatching {
                regOperator.toInt()
            }.getOrElse { e ->
                throw Exception(::toInt.name, e)
            }
            colabRepository.hasByReg(regOperatorInt).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}