package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface HasRegColab {
    suspend operator fun invoke(regOperator: String): Result<Boolean>
}

class IHasRegColab @Inject constructor(
    private val colabRepository: ColabRepository
): HasRegColab {

    override suspend fun invoke(regOperator: String): Result<Boolean> =
        call(getClassAndMethod()) {
            val regOperatorInt = tryCatch(::toInt.name) {
                regOperator.toInt()
            }
            colabRepository.hasByReg(regOperatorInt).getOrThrow()
        }

}