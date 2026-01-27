package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasNoteMotoMec {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasNoteMotoMec @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): HasNoteMotoMec {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            motoMecRepository.hasNoteByIdHeader(id).getOrThrow()
        }

}