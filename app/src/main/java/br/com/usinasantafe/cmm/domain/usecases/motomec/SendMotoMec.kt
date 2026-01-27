package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SendMotoMec {
    suspend operator fun invoke(): EmptyResult
}

class ISendMotoMec @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val configRepository: ConfigRepository,
    private val getToken: GetToken
): SendMotoMec {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            val number = configRepository.getNumber().getOrThrow()
            val token = getToken().getOrThrow()
            motoMecRepository.send(number = number, token = token).getOrThrow()
        }

}