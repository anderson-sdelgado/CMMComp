package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface UncouplingTrailer {
    suspend operator fun invoke(): EmptyResult
}

class IUncouplingTrailer @Inject constructor(
    private val cecRepository: CECRepository
): UncouplingTrailer {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            cecRepository.uncouplingTrailer().getOrThrow()
        }

}