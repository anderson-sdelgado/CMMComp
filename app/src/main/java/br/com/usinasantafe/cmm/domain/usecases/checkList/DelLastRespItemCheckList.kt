package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface DelLastRespItemCheckList {
    suspend operator fun invoke(): EmptyResult
}

class IDelLastRespItemCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository
): DelLastRespItemCheckList {

    override suspend fun invoke(): EmptyResult {
        return runCatching {
            checkListRepository.delLastRespItem().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}