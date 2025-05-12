package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import javax.inject.Inject

interface SetNroOS {
    suspend operator fun invoke(
        nroOS: String
    ): Result<Boolean>
}

class ISetNroOS @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository
): SetNroOS {

    override suspend fun invoke(
        nroOS: String
    ): Result<Boolean> {
        try {
            val result = headerMotoMecRepository.setNroOS(
                nroOS.toInt()
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetNroOS",
                message = "-",
                cause = e
            )
        }
    }

}