package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import javax.inject.Inject

interface SetIdTurn {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ISetIdTurn @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository
): SetIdTurn {

    override suspend fun invoke(id: Int): Result<Boolean> {
        val result = headerMotoMecRepository.setIdTurn(id)
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetIdTurn",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

}