package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
        try {
            val resultHeaderSetId = motoMecRepository.setIdActivityHeader(id)
            resultHeaderSetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if(flowApp == FlowApp.HEADER_INITIAL) return Result.success(flowApp)
            val resultNoteSetId = motoMecRepository.setIdActivityNote(id)
            resultNoteSetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if(flowApp == FlowApp.NOTE_STOP) return Result.success(flowApp)
            val resultListFunctionActivity =
                functionActivityRepository.listById(id)
            resultListFunctionActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val functionActivityList = resultListFunctionActivity.getOrNull()!!
            val checkTranshipment = functionActivityList.any { it.typeActivity == TypeActivity.TRANSHIPMENT }
            if(checkTranshipment) return Result.success(FlowApp.TRANSHIPMENT)
            val checkPerformance = functionActivityList.any { it.typeActivity == TypeActivity.PERFORMANCE }
            if(checkPerformance){
                val result = motoMecRepository.insertInitialPerformance()
                result.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
            }
            val resultGetId = motoMecRepository.getIdByHeaderOpen()
            resultGetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val resultSave = motoMecRepository.saveNote(idHeader)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            startWorkManager()
            val resultGetTypeEquip = motoMecRepository.getTypeEquipHeader()
            resultGetTypeEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val typeEquip = resultGetTypeEquip.getOrNull()!!
            if(typeEquip == TypeEquip.REEL_FERT) return Result.success(FlowApp.REEL_FERT)
            return Result.success(flowApp)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}