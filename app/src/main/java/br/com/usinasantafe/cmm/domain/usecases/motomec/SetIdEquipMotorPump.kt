package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.common.primitives.UnsignedInts.toLong
import javax.inject.Inject

interface SetIdEquipMotorPump {
    suspend operator fun invoke(nroEquip: String): EmptyResult
}

class ISetIdEquipMotorPump @Inject constructor(
    private val equipRepository: EquipRepository,
    private val motoMecRepository: MotoMecRepository,
    private val startWorkManager: StartWorkManager
): SetIdEquipMotorPump {

    override suspend fun invoke(nroEquip: String): EmptyResult {
        return runCatching {
            val nroEquipLong = runCatching {
                nroEquip.toLong()
            }.getOrElse { e ->
                throw Exception(::toLong.name, e)
            }
            val idEquip = equipRepository.getIdByNro(nroEquipLong).getOrThrow()
            motoMecRepository.setIdEquipMotorPumpHeader(idEquip).getOrThrow()
            motoMecRepository.saveHeader().getOrThrow()
            startWorkManager()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}