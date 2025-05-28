package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import javax.inject.Inject

interface SaveHeaderOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class ISaveHeaderOpen @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository
): SaveHeaderOpen {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val result = headerMotoMecRepository.save()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ISaveHeaderOpen",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "ISaveHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

}