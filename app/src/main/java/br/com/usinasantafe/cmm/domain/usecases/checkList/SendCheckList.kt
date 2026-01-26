package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SendCheckList {
    suspend operator fun invoke(): EmptyResult
}

class ISendCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val configRepository: ConfigRepository,
    private val getToken: GetToken
): SendCheckList {

    override suspend fun invoke(): EmptyResult {
        return runCatching {
            val number = configRepository.getNumber().getOrThrow()
            val token = getToken().getOrThrow()
            checkListRepository.send(number = number, token = token).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}