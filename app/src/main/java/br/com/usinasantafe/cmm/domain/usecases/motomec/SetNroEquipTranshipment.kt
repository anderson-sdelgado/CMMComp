package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.common.primitives.UnsignedInts.toLong
import javax.inject.Inject

interface SetNroEquipTranshipment {
    suspend operator fun invoke(
        nroEquipTranshipment: String,
        flowApp: FlowApp
    ): EmptyResult
}

class ISetNroEquipTranshipment @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
): SetNroEquipTranshipment {
    override suspend fun invoke(
        nroEquipTranshipment: String,
        flowApp: FlowApp
    ): EmptyResult {
        return runCatching {
            if (flowApp == FlowApp.TRANSHIPMENT) {
                val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
                val idActivity = motoMecRepository.getIdActivityHeader().getOrThrow()
                motoMecRepository.setNroOSNote(nroOS).getOrThrow()
                motoMecRepository.setIdActivityNote(idActivity).getOrThrow()
            }

            val nroEquipTranshipmentLong = runCatching {
                nroEquipTranshipment.toLong()
            }.getOrElse { e ->
                throw Exception(::toLong.name, e)
            }

            motoMecRepository.setNroEquipTranshipmentNote(nroEquipTranshipmentLong).getOrThrow()
            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            motoMecRepository.saveNote(idHeader).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}