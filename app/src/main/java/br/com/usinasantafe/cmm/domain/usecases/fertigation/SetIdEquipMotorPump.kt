package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.tryCatch
import com.google.common.primitives.UnsignedInts.toLong
import javax.inject.Inject

interface SetIdEquipMotorPump {
    suspend operator fun invoke(nroEquip: String): EmptyResult
}

class ISetIdEquipMotorPump @Inject constructor(
    private val equipRepository: EquipRepository,
    private val motoMecRepository: MotoMecRepository,
    private val fertigationRepository: FertigationRepository,
    private val startWorkManager: StartWorkManager
): SetIdEquipMotorPump {

    override suspend fun invoke(nroEquip: String): EmptyResult =
        call(getClassAndMethod()) {
            val nroEquipLong = tryCatch(::toLong.name) {
                nroEquip.toLong()
            }
            val idEquip = equipRepository.getIdByNro(nroEquipLong).getOrThrow()
            val hourMeter = equipRepository.getHourMeter().getOrThrow()
            fertigationRepository.setIdEquipMotorPump(idEquip).getOrThrow()
            motoMecRepository.saveHeader(hourMeter).getOrThrow()
            startWorkManager()
        }

}