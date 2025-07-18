package br.com.usinasantafe.cmm.domain.usecases.note

import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckHasNoteHeaderOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHasNoteHeaderOpen @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): CheckHasNoteHeaderOpen {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetId = motoMecRepository.getIdByHeaderOpen()
            if (resultGetId.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetId.exceptionOrNull()!!
                )
            }
            val id = resultGetId.getOrNull()!!
            val resultCheck = motoMecRepository.checkNoteHasByIdHeader(id)
            if (resultCheck.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultCheck.exceptionOrNull()!!
                )
            }
            return resultCheck
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}