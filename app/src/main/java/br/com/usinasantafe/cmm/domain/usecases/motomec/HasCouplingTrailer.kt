package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasCouplingTrailer {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasCouplingTrailer @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): HasCouplingTrailer {

    override suspend fun invoke(): Result<Boolean> {
        val result = motoMecRepository.hasCouplingTrailerImplement()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}