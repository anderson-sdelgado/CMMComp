package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
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
        return runCatching {
            motoMecRepository.getNroOSHeader().getOrThrow().toString()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}