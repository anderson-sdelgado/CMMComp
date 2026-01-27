package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface SetRegOperator {
    suspend operator fun invoke(
        regOperator: String
    ): EmptyResult
}

class ISetRegOperator @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetRegOperator {

    override suspend fun invoke(
        regOperator: String
    ): EmptyResult =
        call(getClassAndMethod()) {
            val regOperatorInt = tryCatch(::toInt.name) {
                regOperator.toInt()
            }
            motoMecRepository.setRegOperatorHeader(regOperatorInt).getOrThrow()
        }

}