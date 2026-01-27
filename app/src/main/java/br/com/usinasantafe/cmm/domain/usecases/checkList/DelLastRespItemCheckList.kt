package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

interface DelLastRespItemCheckList {
    suspend operator fun invoke(): EmptyResult
}

class IDelLastRespItemCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository
): DelLastRespItemCheckList {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            checkListRepository.delLastRespItem().getOrThrow()
        }

}