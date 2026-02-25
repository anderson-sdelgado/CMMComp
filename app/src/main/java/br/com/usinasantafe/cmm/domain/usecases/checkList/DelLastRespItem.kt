package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

interface DelLastRespItem {
    suspend operator fun invoke(): EmptyResult
}

class IDelLastRespItem @Inject constructor(
    private val checkListRepository: CheckListRepository
): DelLastRespItem {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            checkListRepository.delLastRespItem().getOrThrow()
        }

}