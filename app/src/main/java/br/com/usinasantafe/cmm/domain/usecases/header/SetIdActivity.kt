package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import javax.inject.Inject

interface SetIdActivity {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class ISetIdActivity @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository
): SetIdActivity {

    override suspend fun invoke(id: Int): Result<Boolean> {
        val result = headerMotoMecRepository.setIdActivity(id)
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ISetIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

}