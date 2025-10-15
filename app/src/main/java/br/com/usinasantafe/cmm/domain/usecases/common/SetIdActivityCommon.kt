package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.cmm.utils.FlowApp
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
    private val startWorkManager: StartWorkManager
): SetIdActivityCommon {

    override suspend fun invoke(
        id: Int,
        flowApp: FlowApp
    ): Result<Boolean> {
        try {
            val resultHeaderSetId = motoMecRepository.setIdActivityHeader(id)
            if(resultHeaderSetId.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultHeaderSetId.exceptionOrNull()!!
                )
            }
            if(flowApp == FlowApp.HEADER_INITIAL) return resultHeaderSetId
            val resultNoteSetId = motoMecRepository.setIdActivityNote(id)
            if(resultNoteSetId.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultNoteSetId.exceptionOrNull()!!
                )
            }
            if(flowApp != FlowApp.NOTE_WORK) return resultNoteSetId
            val resultGetId = motoMecRepository.getIdByHeaderOpen()
            if(resultGetId.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetId.exceptionOrNull()!!
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val resultSave = motoMecRepository.saveNote(idHeader)
            if(resultSave.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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