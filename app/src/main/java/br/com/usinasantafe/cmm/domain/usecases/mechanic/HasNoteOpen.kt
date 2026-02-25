package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasNoteOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasNoteOpen @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val mechanicRepository: MechanicRepository
): HasNoteOpen {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            mechanicRepository.hasNoteOpenByIdHeader(idHeader).getOrThrow()
        }

}