package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckHasNoteOpenMechanic {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckHasNoteOpenMechanic @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val mechanicRepository: MechanicRepository
): CheckHasNoteOpenMechanic {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetIdHeader = motoMecRepository.getIdByHeaderOpen()
            if(resultGetIdHeader.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdHeader.exceptionOrNull()!!
                )
            }
            val idHeader = resultGetIdHeader.getOrNull()!!
            val resultGetCheck = mechanicRepository.checkNoteOpenByIdHeader(idHeader)
            if(resultGetCheck.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetCheck.exceptionOrNull()!!
                )
            }
            val check = resultGetCheck.getOrNull()!!
            return Result.success(check)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}