package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
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
        return runCatching {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val check = motoMecRepository.hasNoteByIdHeader(id).getOrThrow()
            if (!check) return Result.success(null)
            val noteLast = motoMecRepository.getNoteLastByIdHeader(id).getOrThrow()
            if(noteLast.idStop == null) return Result.success(TypeNote.WORK)
            TypeNote.STOP
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}