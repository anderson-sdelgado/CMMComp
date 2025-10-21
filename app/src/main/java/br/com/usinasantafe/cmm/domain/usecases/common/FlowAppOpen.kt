package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.FlowApp
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
            val resultCheckMotoMecOpen = motoMecRepository.checkHeaderOpen()
            if(resultCheckMotoMecOpen.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckMotoMecOpen.exceptionOrNull()!!
                )
            }
            val checkMotoMecOpen = resultCheckMotoMecOpen.getOrNull()!!
            if(!checkMotoMecOpen) return Result.success(FlowApp.HEADER_INITIAL)
            val resultCheckCheckListOpen = checkListRepository.checkOpen()
            if(resultCheckCheckListOpen.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckCheckListOpen.exceptionOrNull()!!
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