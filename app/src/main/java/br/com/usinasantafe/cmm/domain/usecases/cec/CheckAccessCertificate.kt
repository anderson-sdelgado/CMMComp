package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface CheckAccessCertificate {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessCertificate @Inject constructor(
    private val cecRepository: CECRepository
): CheckAccessCertificate {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val model = cecRepository.get().getOrThrow()
            (model.dateExitMill != null) && (model.dateFieldArrival != null)
        }

}