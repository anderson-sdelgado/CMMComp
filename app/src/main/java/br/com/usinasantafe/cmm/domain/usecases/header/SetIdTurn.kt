package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdTurn {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ISetIdTurn @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetIdTurn {

    override suspend fun invoke(id: Int): Result<Boolean> {
        val result = motoMecRepository.setIdTurnHeader(id)
        if(result.isFailure){
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}