package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckSendCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository
): CheckSendCheckList {

    override suspend fun invoke(): Result<Boolean> {
        val result = checkListRepository.hasSend()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}