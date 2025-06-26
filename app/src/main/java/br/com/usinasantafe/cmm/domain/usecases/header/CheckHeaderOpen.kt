package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import javax.inject.Inject

interface CheckHeaderOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHeaderOpen @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): CheckHeaderOpen {

    override suspend fun invoke(): Result<Boolean> {
        val result = motoMecRepository.checkOpenHeader()
        if(result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckHeaderOpen",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}