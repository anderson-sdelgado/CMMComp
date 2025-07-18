package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SendHeader {
    suspend operator fun invoke(): Result<Boolean>
}

class ISendHeader @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val configRepository: ConfigRepository,
    private val getToken: GetToken
): SendHeader {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGet = configRepository.getNumber()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val number = resultGet.getOrNull()!!
            val resultToken = getToken()
            if(resultToken.isFailure){
                return resultFailureMiddle(
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
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}