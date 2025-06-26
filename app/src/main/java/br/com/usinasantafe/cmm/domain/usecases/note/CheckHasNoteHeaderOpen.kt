package br.com.usinasantafe.cmm.domain.usecases.note

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import javax.inject.Inject

interface CheckHasNoteHeaderOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHasNoteHeaderOpen @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): CheckHasNoteHeaderOpen {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetId = motoMecRepository.getIdByOpenHeader()
            if (resultGetId.isFailure) {
                val e = resultGetId.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNoteHeaderOpen",
                    message = e.message,
                    cause = e.cause
                )
            }
            val id = resultGetId.getOrNull()!!
            val resultCheck = motoMecRepository.checkNoteHasByIdHeader(id)
            if (resultCheck.isFailure) {
                val e = resultCheck.exceptionOrNull()!!
                return resultFailure(
                    context = "ICheckNoteHeaderOpen",
                    message = e.message,
                    cause = e.cause
                )
            }
            return resultCheck
        } catch (e: Exception) {
            return resultFailure(
                context = "ICheckNoteHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

}