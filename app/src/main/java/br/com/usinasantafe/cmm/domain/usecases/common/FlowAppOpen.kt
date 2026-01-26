package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface FlowAppOpen {
    suspend operator fun invoke(): Result<FlowApp>
}

class IFlowAppOpen @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val checkListRepository: CheckListRepository
): FlowAppOpen {

    override suspend fun invoke(): Result<FlowApp> {
        return runCatching {
            val hasMotoMecOpen = motoMecRepository.hasHeaderOpen().getOrThrow()
            if(!hasMotoMecOpen) return Result.success(FlowApp.HEADER_INITIAL)
            motoMecRepository.refreshHeaderOpen().getOrThrow()
            val hasCheckListOpen = checkListRepository.hasOpen().getOrThrow()
            if(hasCheckListOpen) return Result.success(FlowApp.CHECK_LIST)
            FlowApp.NOTE_WORK
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}