package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
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
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendHeader",
                    message = e.message,
                    cause = e.cause
                )
            }
            val number = resultGet.getOrNull()!!
            val resultToken = getToken()
            if(resultToken.isFailure){
                val e = resultToken.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendHeader",
                    message = e.message,
                    cause = e.cause
                )
            }
            val token = resultToken.getOrNull()!!
            val result = motoMecRepository.send(
                number = number,
                token = token
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISendHeader",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ISendHeader",
                message = "-",
                cause = e
            )
        }
    }

}