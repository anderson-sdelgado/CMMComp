package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.cmm.utils.FlowApp
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
                val e = resultHeaderSetId.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdActivityCommon",
                    message = e.message,
                    cause = e.cause
                )
            }
            if(flowApp == FlowApp.HEADER_INITIAL) return resultHeaderSetId
            val resultNoteSetId = motoMecRepository.setIdActivityNote(id)
            if(resultNoteSetId.isFailure){
                val e = resultNoteSetId.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdActivityCommon",
                    message = e.message,
                    cause = e.cause
                )
            }
            if(flowApp != FlowApp.NOTE_WORK) return resultNoteSetId
            val resultGetId = motoMecRepository.getIdByOpenHeader()
            if(resultGetId.isFailure){
                val e = resultGetId.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdActivityCommon",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val resultSave = motoMecRepository.saveNote(idHeader)
            if(resultSave.isFailure){
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetIdActivityCommon",
                    message = e.message,
                    cause = e.cause
                )
            }
            startWorkManager()
            return resultSave
        } catch (e: Exception){
            return resultFailure(
                context = "ISetIdActivityCommon",
                message = "-",
                cause = e
            )
        }
    }

}