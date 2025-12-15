package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeNote
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetTypeLastNote {
    suspend operator fun invoke(): Result<TypeNote?>
}

class IGetTypeLastNote @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): GetTypeLastNote {

    override suspend fun invoke(): Result<TypeNote?> {
        try {
            val resultGetId = motoMecRepository.getIdByHeaderOpen()
            resultGetId.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val id = resultGetId.getOrNull()!!
            val resultHasNote = motoMecRepository.hasNoteByIdHeader(id)
            resultHasNote.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val check = resultHasNote.getOrNull()!!
            if (!check) return Result.success(null)
            val resultGetNoteLast = motoMecRepository.getNoteLastByIdHeader(id)
            resultGetNoteLast.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val noteLast = resultGetNoteLast.getOrNull()!!
            if(noteLast.idStop == null) return Result.success(TypeNote.WORK)
            return Result.success(TypeNote.STOP)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}