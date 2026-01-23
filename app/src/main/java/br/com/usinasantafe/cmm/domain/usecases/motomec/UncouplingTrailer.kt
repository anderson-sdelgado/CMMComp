package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface UncouplingTrailer {
    suspend operator fun invoke(): EmptyResult
}

class IUncouplingTrailer @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): UncouplingTrailer {

    override suspend fun invoke(): EmptyResult {
        val result = motoMecRepository.uncouplingTrailerImplement()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}