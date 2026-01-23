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
        try {
            val resultCheckMotoMecOpen = motoMecRepository.hasHeaderOpen()
            resultCheckMotoMecOpen.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val checkMotoMecOpen = resultCheckMotoMecOpen.getOrNull()!!
            if(!checkMotoMecOpen) return Result.success(FlowApp.HEADER_INITIAL)
            val resultRefresh = motoMecRepository.refreshHeaderOpen()
            resultRefresh.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultCheckCheckListOpen = checkListRepository.checkOpen()
            resultCheckCheckListOpen.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val checkCheckListOpen = resultCheckCheckListOpen.getOrNull()!!
            if(checkCheckListOpen) return Result.success(FlowApp.CHECK_LIST)
            return Result.success(FlowApp.NOTE_WORK)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}