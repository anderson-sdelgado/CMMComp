package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasCouplingTrailer {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasCouplingTrailer @Inject constructor(
    private val cecRepository: CECRepository
): HasCouplingTrailer {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            cecRepository.hasCouplingTrailer().getOrThrow()
        }

}