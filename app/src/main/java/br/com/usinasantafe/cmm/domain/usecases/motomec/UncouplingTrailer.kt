package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface UncouplingTrailer {
    suspend operator fun invoke(): EmptyResult
}

class IUncouplingTrailer @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): UncouplingTrailer {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            motoMecRepository.uncouplingTrailerImplement().getOrThrow()
        }

}