package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNroOSCommon {
    suspend operator fun invoke(
        nroOS: String,
        flowApp: FlowApp = FlowApp.HEADER_INITIAL
    ): Result<Boolean>
}

class ISetNroOSCommon @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetNroOSCommon {

    override suspend fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): Result<Boolean> {
        try {
            val resultSetNroOSHeader = motoMecRepository.setNroOSHeader(
                nroOS.toInt()
            )
            if (resultSetNroOSHeader.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSetNroOSHeader.exceptionOrNull()!!
                )
            }
            if (flowApp == FlowApp.HEADER_INITIAL) return resultSetNroOSHeader
            val resultSetNroOSNote = motoMecRepository.setNroOSNote(
                nroOS.toInt()
            )
            if (resultSetNroOSNote.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSetNroOSNote.exceptionOrNull()!!
                )
            }
            return resultSetNroOSNote
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}