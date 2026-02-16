package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetIdNozzle {
    suspend operator fun invoke(id: Int): Result<Unit>
}

class ISetIdNozzle @Inject constructor(
    private val fertigationRepository: FertigationRepository
): SetIdNozzle {

    override suspend fun invoke(id: Int): Result<Unit> =
        call(getClassAndMethod()) {
            fertigationRepository.setIdNozzle(id).getOrThrow()
        }

}