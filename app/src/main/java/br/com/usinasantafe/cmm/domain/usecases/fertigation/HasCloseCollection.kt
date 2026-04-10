package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasCloseCollection {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasCloseCollection @Inject constructor(
    private val fertigationRepository: FertigationRepository
): HasCloseCollection {

    override suspend fun invoke(): Result<Boolean> =
        call(getClassAndMethod()) {
            val check = !fertigationRepository.hasCollectionByValueNull().getOrThrow()
            return@call check
        }

}