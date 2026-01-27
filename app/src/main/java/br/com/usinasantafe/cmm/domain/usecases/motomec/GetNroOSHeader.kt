package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetNroOSHeader {
    suspend operator fun invoke(): Result<String>
}

class IGetNroOSHeader @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): GetNroOSHeader {

    override suspend fun invoke(): Result<String> =
        call(getClassAndMethod()) {
            motoMecRepository.getNroOSHeader().getOrThrow().toString()
        }

}