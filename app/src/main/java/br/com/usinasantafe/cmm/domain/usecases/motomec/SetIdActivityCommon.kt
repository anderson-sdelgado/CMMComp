package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdActivityCommon {
    suspend operator fun invoke(
        id: Int,
        flowApp: FlowApp = FlowApp.HEADER_INITIAL
    ): Result<Boolean>
}

class ISetIdActivityCommon @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val functionActivityRepository: FunctionActivityRepository,
    private val startWorkManager: StartWorkManager
): SetIdActivityCommon {

    override suspend fun invoke(
        id: Int,
        flowApp: FlowApp
    ): Result<Boolean> {
        try {
            val resultHeaderSetId = motoMecRepository.setIdActivityHeader(id)
            resultHeaderSetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if(flowApp == FlowApp.HEADER_INITIAL) return resultHeaderSetId
            val resultNoteSetId = motoMecRepository.setIdActivityNote(id)
            resultNoteSetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if(flowApp != FlowApp.NOTE_WORK) return resultNoteSetId
            val resultListFunctionActivity =
                functionActivityRepository.listByIdActivity(id)
            resultListFunctionActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val functionActivityList = resultListFunctionActivity.getOrNull()!!
            val checkTranshipment = functionActivityList.any { it.typeActivity == TypeActivity.TRANSHIPMENT }
            if(checkTranshipment) return Result.success(false)
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
            return resultSave
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}