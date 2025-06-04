package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.NoteMotoMecRepository
import br.com.usinasantafe.cmm.utils.FlowApp
import javax.inject.Inject

interface SetIdActivityCommon {
    suspend operator fun invoke(
        id: Int,
        flowApp: FlowApp = FlowApp.HEADER_DEFAULT
    ): Result<Boolean>
}

class ISetIdActivityCommon @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository,
    private val noteMotoMecRepository: NoteMotoMecRepository
): SetIdActivityCommon {

    override suspend fun invoke(
        id: Int,
        flowApp: FlowApp
    ): Result<Boolean> {
        val resultHeaderSetId = headerMotoMecRepository.setIdActivity(id)
        if(resultHeaderSetId.isFailure){
            val e = resultHeaderSetId.exceptionOrNull()!!
            return resultFailure(
                context = "ISetIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        if(flowApp == FlowApp.HEADER_DEFAULT) return resultHeaderSetId
        val resultNoteSetId = noteMotoMecRepository.setIdActivity(id)
        if(resultNoteSetId.isFailure){
            val e = resultNoteSetId.exceptionOrNull()!!
            return resultFailure(
                context = "ISetIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        if(flowApp != FlowApp.NOTE_WORK) return resultNoteSetId
        val resultGetId = headerMotoMecRepository.getIdByHeaderOpen()
        if(resultGetId.isFailure){
            val e = resultGetId.exceptionOrNull()!!
            return resultFailure(
                context = "ISetIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        val idHeader = resultGetId.getOrNull()!!
        val resultSave = noteMotoMecRepository.save(idHeader)
        if(resultSave.isFailure){
            val e = resultSave.exceptionOrNull()!!
            return resultFailure(
                context = "ISetIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        return resultSave
    }

}