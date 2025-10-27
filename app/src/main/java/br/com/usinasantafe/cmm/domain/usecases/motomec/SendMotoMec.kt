package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SendMotoMec {
    suspend operator fun invoke(): Result<Boolean>
}

class ISendMotoMec @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val configRepository: ConfigRepository,
    private val getToken: GetToken
): SendMotoMec {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGet = configRepository.getNumber()
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val number = resultGet.getOrNull()!!
            val resultToken = getToken()
            if(resultToken.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultToken.exceptionOrNull()!!
                )
            }
            val token = resultToken.getOrNull()!!
            val result = motoMecRepository.send(
                number = number,
                token = token
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