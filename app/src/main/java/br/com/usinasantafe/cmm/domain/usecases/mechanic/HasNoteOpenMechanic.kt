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
        return runCatching {
            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            mechanicRepository.hasNoteOpenByIdHeader(idHeader).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}