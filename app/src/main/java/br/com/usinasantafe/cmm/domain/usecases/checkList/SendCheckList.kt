package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SendCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ISendCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val configRepository: ConfigRepository,
    private val getToken: GetToken
): SendCheckList {

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
            val result = checkListRepository.send(
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