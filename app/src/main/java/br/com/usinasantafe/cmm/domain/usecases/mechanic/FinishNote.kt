package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface FinishNote {
    suspend operator fun invoke(): EmptyResult
}

class IFinishNote @Inject constructor(
    private val mechanicRepository: MechanicRepository
): FinishNote {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            mechanicRepository.setFinishNote().getOrThrow()
        }

}