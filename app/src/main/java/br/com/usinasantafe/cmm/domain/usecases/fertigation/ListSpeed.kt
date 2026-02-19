package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.domain.repositories.stable.PressureRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListSpeed {
    suspend operator fun invoke(): Result<List<Pressure>>
}

class IListSpeed @Inject constructor(
    private val fertigationRepository: FertigationRepository,
    private val pressureRepository: PressureRepository
): ListSpeed {

    override suspend fun invoke(): Result<List<Pressure>> =
        call(getClassAndMethod()) {
            val idNozzle = fertigationRepository.getIdNozzle().getOrThrow()
            val valuePressure = fertigationRepository.getValuePressure().getOrThrow()
            pressureRepository.listByIdNozzleAndValuePressure(idNozzle, valuePressure).getOrThrow()
        }

}