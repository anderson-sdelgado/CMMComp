package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetNroOSHeader {
    suspend operator fun invoke(): Result<String>
}

class IGetNroOSHeader @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): GetNroOSHeader {

    override suspend fun invoke(): Result<String> {
        try {
            val result = motoMecRepository.getNroOSHeader()
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val nroOS = result.getOrNull()!!
            return Result.success(nroOS.toString())
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}