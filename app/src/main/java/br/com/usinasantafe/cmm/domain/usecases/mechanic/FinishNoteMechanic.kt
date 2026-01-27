package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface FinishNoteMechanic {
    suspend operator fun invoke(): EmptyResult
}

class IFinishNoteMechanic @Inject constructor(
    private val mechanicRepository: MechanicRepository
): FinishNoteMechanic {

    override suspend fun invoke(): EmptyResult =
        call(getClassAndMethod()) {
            mechanicRepository.setFinishNote().getOrThrow()
        }

}