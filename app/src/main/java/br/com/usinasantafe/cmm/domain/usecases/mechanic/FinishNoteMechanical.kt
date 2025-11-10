package br.com.usinasantafe.cmm.domain.usecases.mechanic

import javax.inject.Inject

interface FinishNoteMechanical {
    suspend operator fun invoke(): Result<Boolean>
}

class IFinishNoteMechanical @Inject constructor(
): FinishNoteMechanical {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}