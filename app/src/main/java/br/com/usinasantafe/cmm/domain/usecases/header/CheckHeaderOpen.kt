package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckHeaderOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHeaderOpen @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): CheckHeaderOpen {

    override suspend fun invoke(): Result<Boolean> {
        val result = motoMecRepository.checkHeaderOpen()
        if(result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}