package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import javax.inject.Inject

interface GetNroOSHeader {
    suspend operator fun invoke(): Result<String>
}

class IGetNroOSHeader @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository
): GetNroOSHeader {

    override suspend fun invoke(): Result<String> {
        try {
            val result = headerMotoMecRepository.getNroOS()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetNroOSHeader",
                    message = e.message,
                    cause = e.cause
                )
            }
            val nroOS = result.getOrNull()!!
            return Result.success(nroOS.toString())
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetNroOSHeader",
                message = "-",
                cause = e
            )
        }
    }

}