package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import javax.inject.Inject

interface CheckHeaderSend {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHeaderSend @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): CheckHeaderSend {

    override suspend fun invoke(): Result<Boolean> {
        val result = motoMecRepository.checkSendHeader()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ICheckHeaderSend",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}