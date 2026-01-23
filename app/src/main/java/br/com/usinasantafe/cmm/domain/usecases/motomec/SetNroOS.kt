package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNroOS {
    suspend operator fun invoke(
        nroOS: String,
        flowApp: FlowApp = FlowApp.HEADER_INITIAL
    ): EmptyResult
}

class ISetNroOS @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetNroOS {

    override suspend fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): EmptyResult {
        return runCatching {
            motoMecRepository.setNroOSHeader(
                nroOS.toInt()
            ).getOrThrow()
            if (flowApp == FlowApp.HEADER_INITIAL) return Result.success(Unit)
            motoMecRepository.setNroOSNote(
                nroOS.toInt()
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}