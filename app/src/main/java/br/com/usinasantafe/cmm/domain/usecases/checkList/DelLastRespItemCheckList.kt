package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface DelLastRespItemCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class IDelLastRespItemCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository
): DelLastRespItemCheckList {

    override suspend fun invoke(): Result<Boolean> {
        val result = checkListRepository.delLastRespItem()
        result.onFailure { return Result.failure(it) }
        return result
    }

}