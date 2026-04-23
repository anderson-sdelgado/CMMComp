package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface CheckIdRelease {
    suspend operator fun invoke(
        idRelease: String
    ): Result<Boolean>
}

class ICheckIdRelease @Inject constructor(
    private val osRepository: OSRepository,
    private val motoMecRepository: MotoMecRepository
): CheckIdRelease {

    override suspend operator fun invoke(
        idRelease: String
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            val idReleaseInt = tryCatch(::toInt.name) {
                idRelease.toInt()
            }
            val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
            osRepository.hasByNroAndIdRelease(nroOS, idReleaseInt).getOrThrow()
        }

}