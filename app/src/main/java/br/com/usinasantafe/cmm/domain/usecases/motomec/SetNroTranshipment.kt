package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNroTranshipment {
    suspend operator fun invoke(
        nroTranshipment: String,
        flowApp: FlowApp
    ): Result<Boolean>
}

class ISetNroTranshipment @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
): SetNroTranshipment {

    override suspend fun invoke(
        nroTranshipment: String,
        flowApp: FlowApp
    ): Result<Boolean> {
        return runCatching {

            if(flowApp == FlowApp.TRANSHIPMENT){
                val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
                val idActivity = motoMecRepository.getIdActivityHeader().getOrThrow()

                motoMecRepository.setNroOSNote(nroOS).getOrThrow()
                motoMecRepository.setIdActivityNote(idActivity).getOrThrow()
            }

            motoMecRepository.setNroTranshipmentNote(nroTranshipment.toLong()).getOrThrow()

            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            motoMecRepository.saveNote(idHeader).getOrThrow()

        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}