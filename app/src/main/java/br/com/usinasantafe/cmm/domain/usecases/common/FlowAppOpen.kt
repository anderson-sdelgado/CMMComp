package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface FlowAppOpen {
    suspend operator fun invoke(): Result<FlowApp>
}

class IFlowAppOpen @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val checkListRepository: CheckListRepository
): FlowAppOpen {

    override suspend fun invoke(): Result<FlowApp> =
        call(getClassAndMethod()) {
            val hasMotoMecOpen = motoMecRepository.hasHeaderOpen().getOrThrow()
            if(!hasMotoMecOpen) return@call FlowApp.HEADER_INITIAL
            motoMecRepository.refreshHeaderOpen().getOrThrow()
            val hasCheckListOpen = checkListRepository.hasOpen().getOrThrow()
            if(hasCheckListOpen) return@call FlowApp.CHECK_LIST
            FlowApp.NOTE_WORK
        }

}