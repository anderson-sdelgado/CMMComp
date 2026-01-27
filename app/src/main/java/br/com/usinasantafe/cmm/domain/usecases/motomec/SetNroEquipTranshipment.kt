package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
    private val functionActivityRepository: FunctionActivityRepository
): SetNroEquipTranshipment {
    override suspend fun invoke(
        nroEquipTranshipment: String,
        flowApp: FlowApp
    ): EmptyResult =
        call(getClassAndMethod()) {
            val idActivity = motoMecRepository.getIdActivityHeader().getOrThrow()

            if (flowApp == FlowApp.TRANSHIPMENT) {
                val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
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

            val checkPerformance = functionActivityRepository.hasByIdAndType(
                idActivity = idActivity,
                typeActivity = TypeActivity.PERFORMANCE
            ).getOrThrow()

            if(checkPerformance) motoMecRepository.insertInitialPerformance().getOrThrow()
        }

}