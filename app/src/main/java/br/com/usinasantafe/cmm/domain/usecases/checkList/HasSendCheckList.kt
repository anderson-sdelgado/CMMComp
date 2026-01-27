package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

interface CheckSendCheckList {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository
): CheckSendCheckList {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            checkListRepository.hasSend().getOrThrow()
        }

}