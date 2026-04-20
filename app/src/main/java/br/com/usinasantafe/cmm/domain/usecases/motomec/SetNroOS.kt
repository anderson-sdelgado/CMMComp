package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface SetNroOS {
    suspend operator fun invoke(
        nroOS: String
    ): EmptyResult
}

class ISetNroOS @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetNroOS {

    override suspend fun invoke(
        nroOS: String
    ): EmptyResult =
        call(getClassAndMethod()) {
            val nroOSInt = tryCatch(::toInt.name) {
                nroOS.toInt()
            }
            motoMecRepository.setNroOSHeader(nroOSInt).getOrThrow()
        }

}