package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

interface SendCheckList {
    suspend operator fun invoke(): EmptyResult
}

class ISendCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val configRepository: ConfigRepository,
    private val getToken: GetToken
): SendCheckList {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            val number = configRepository.getNumber().getOrThrow()
            val token = getToken().getOrThrow()
            checkListRepository.send(number = number, token = token).getOrThrow()
        }

}