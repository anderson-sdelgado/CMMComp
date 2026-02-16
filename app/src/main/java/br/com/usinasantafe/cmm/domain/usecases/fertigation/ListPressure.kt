package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.domain.repositories.stable.PressureRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListPressure {
    suspend operator fun invoke(): Result<List<Pressure>>
}

class IListPressure @Inject constructor(
    private val fertigationRepository: FertigationRepository,
    private val pressureRepository: PressureRepository
): ListPressure {

    override suspend fun invoke(): Result<List<Pressure>> =
        call(getClassAndMethod()) {
            val id = fertigationRepository.getIdNozzle().getOrThrow()
            pressureRepository.listByIdNozzle(id).getOrThrow()
        }

}