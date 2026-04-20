package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.motomec.SaveNote
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdActivityCommon {
    suspend operator fun invoke(
        id: Int,
        flowApp: FlowApp
    ): Result<FlowApp>
}

class ISetIdActivityCommon @Inject constructor(
    private val equipRepository: EquipRepository,
    private val motoMecRepository: MotoMecRepository,
    private val functionActivityRepository: FunctionActivityRepository,
    private val startWorkManager: StartWorkManager,
    private val saveNote: SaveNote
): SetIdActivityCommon {

    override suspend fun invoke(
        id: Int,
        flowApp: FlowApp
    ): Result<FlowApp> =
        call(getClassAndMethod()) {

            motoMecRepository.setIdActivityHeader(id).getOrThrow()
            if((flowApp == FlowApp.HEADER_INITIAL) || (flowApp == FlowApp.NOTE_STOP) || (flowApp == FlowApp.PRE_CEC)) return@call flowApp

            if(flowApp == FlowApp.HEADER_INITIAL_REEL_FERT){
                val idEquip = equipRepository.getIdEquipMain().getOrThrow()
                val idHeader = motoMecRepository.getIdHeaderByIdEquipAndNotFinish(idEquip).getOrThrow()
                motoMecRepository.saveHeader(0.0, idHeader).getOrThrow()
                return@call flowApp
            }

            val functionActivityList = functionActivityRepository.listById(id).getOrThrow()
            val checkTranshipment = functionActivityList.any { it.typeActivity == TypeActivity.TRANSHIPMENT }
            if(checkTranshipment) return@call FlowApp.TRANSHIPMENT

            val typeEquip = motoMecRepository.getTypeEquipHeader().getOrThrow()
            if(typeEquip == TypeEquip.REEL_FERT) return@call FlowApp.NOTE_REEL_FERT

            saveNote().getOrThrow()
            startWorkManager()

            return@call flowApp
        }

}