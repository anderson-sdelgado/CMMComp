package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdActivityCommon {
    suspend operator fun invoke(
        id: Int,
        flowApp: FlowApp
    ): Result<FlowApp>
}

class ISetIdActivityCommon @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val functionActivityRepository: FunctionActivityRepository,
    private val startWorkManager: StartWorkManager
): SetIdActivityCommon {

    override suspend fun invoke(
        id: Int,
        flowApp: FlowApp
    ): Result<FlowApp> {
        return runCatching {
            motoMecRepository.setIdActivityHeader(id).getOrThrow()
            if(flowApp == FlowApp.HEADER_INITIAL) return Result.success(flowApp)
            motoMecRepository.setIdActivityNote(id).getOrThrow()
            if(flowApp == FlowApp.NOTE_STOP) return Result.success(flowApp)
            val functionActivityList = functionActivityRepository.listById(id).getOrThrow()
            val checkTranshipment = functionActivityList.any { it.typeActivity == TypeActivity.TRANSHIPMENT }
            if(checkTranshipment) return Result.success(FlowApp.TRANSHIPMENT)
            val checkPerformance = functionActivityList.any { it.typeActivity == TypeActivity.PERFORMANCE }
            if(checkPerformance) motoMecRepository.insertInitialPerformance().getOrThrow()
            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            motoMecRepository.saveNote(idHeader).getOrThrow()
            startWorkManager()
            val typeEquip = motoMecRepository.getTypeEquipHeader().getOrThrow()
            if(typeEquip == TypeEquip.REEL_FERT) return Result.success(FlowApp.REEL_FERT)
            flowApp
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}