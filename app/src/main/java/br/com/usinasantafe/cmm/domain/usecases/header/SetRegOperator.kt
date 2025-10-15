package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetRegOperator {
    suspend operator fun invoke(
        regOperator: String
    ): Result<Boolean>
}

class ISetRegOperator @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetRegOperator {

    override suspend fun invoke(
        regOperator: String
    ): Result<Boolean> {
        try {
            val result = motoMecRepository.setRegOperatorHeader(
                regOperator.toInt()
            )
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
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