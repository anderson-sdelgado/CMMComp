package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckHeaderSend {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHeaderSend @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): CheckHeaderSend {

    override suspend fun invoke(): Result<Boolean> {
        val result = motoMecRepository.checkHeaderSend()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}