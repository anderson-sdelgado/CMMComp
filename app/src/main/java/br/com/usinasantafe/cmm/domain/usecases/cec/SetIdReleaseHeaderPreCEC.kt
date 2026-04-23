package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface SetIdRelease {
    suspend operator fun invoke(
        idRelease: String
    ): Result<Boolean>
}

class ISetIdRelease @Inject constructor(
    private val cecRepository: CECRepository
): SetIdRelease {

    override suspend operator fun invoke(
        idRelease: String
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            val idReleaseInt = tryCatch(::toInt.name) {
                idRelease.toInt()
            }
            cecRepository.setIdReleasePreCEC(idReleaseInt).getOrThrow()
        }

}