package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface FinishNoteMechanic {
    suspend operator fun invoke(): EmptyResult
}

class IFinishNoteMechanic @Inject constructor(
    private val mechanicRepository: MechanicRepository
): FinishNoteMechanic {

    override suspend fun invoke(): EmptyResult {
        return runCatching {
            mechanicRepository.setFinishNote().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}