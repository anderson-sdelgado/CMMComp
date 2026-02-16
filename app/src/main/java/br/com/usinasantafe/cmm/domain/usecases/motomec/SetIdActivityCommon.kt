package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
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
            if(flowApp == FlowApp.HEADER_INITIAL) return@call flowApp

            motoMecRepository.setIdActivityNote(id).getOrThrow()
            if(flowApp == FlowApp.NOTE_STOP) return@call flowApp

            val functionActivityList = functionActivityRepository.listById(id).getOrThrow()
            val checkTranshipment = functionActivityList.any { it.typeActivity == TypeActivity.TRANSHIPMENT }
            if(checkTranshipment) return@call FlowApp.TRANSHIPMENT

            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
            saveNote(idHeader, id, nroOS).getOrThrow()

            startWorkManager()

            val typeEquip = motoMecRepository.getTypeEquipHeader().getOrThrow()
            if(typeEquip == TypeEquip.REEL_FERT) return@call FlowApp.REEL_FERT
            return@call flowApp
        }

}