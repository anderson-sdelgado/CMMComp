package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckSendMotoMec {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendMotoMec @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): CheckSendMotoMec {

    override suspend fun invoke(): Result<Boolean> {
        val result = motoMecRepository.checkHeaderSend()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}