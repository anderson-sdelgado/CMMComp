package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
    ): EmptyResult {
        try {
            val result = motoMecRepository.setRegOperatorHeader(
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