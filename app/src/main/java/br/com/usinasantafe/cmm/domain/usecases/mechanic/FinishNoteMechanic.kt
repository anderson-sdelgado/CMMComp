package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface FinishNoteMechanic {
    suspend operator fun invoke(): Result<Boolean>
}

class IFinishNoteMechanic @Inject constructor(
    private val mechanicRepository: MechanicRepository
): FinishNoteMechanic {

    override suspend fun invoke(): Result<Boolean> {
        val result = mechanicRepository.setFinishNote()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return Result.success(true)
    }

}