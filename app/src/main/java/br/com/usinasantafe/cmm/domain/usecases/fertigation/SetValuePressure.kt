package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetValuePressure {
    suspend operator fun invoke(value: Double): Result<Unit>
}

class ISetValuePressure @Inject constructor(
    private val fertigationRepository: FertigationRepository
): SetValuePressure {

    override suspend fun invoke(value: Double): Result<Unit> =
        call(getClassAndMethod()) {
            fertigationRepository.setValuePressure(value).getOrThrow()
        }

}