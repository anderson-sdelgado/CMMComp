package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasSendMotoMec {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasSendMotoMec @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): HasSendMotoMec {

    override suspend fun invoke(): Result<Boolean> {
        return runCatching {
            motoMecRepository.hasHeaderSend().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}