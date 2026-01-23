package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasNoteOpenMechanic {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasNoteOpenMechanic @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val mechanicRepository: MechanicRepository
): HasNoteOpenMechanic {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetIdHeader = motoMecRepository.getIdByHeaderOpen() //ok
            resultGetIdHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idHeader = resultGetIdHeader.getOrNull()!!
            val resultGetCheck = mechanicRepository.hasNoteOpenByIdHeader(idHeader)
            resultGetCheck.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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