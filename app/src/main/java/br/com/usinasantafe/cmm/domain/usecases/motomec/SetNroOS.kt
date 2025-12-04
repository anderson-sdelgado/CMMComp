package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNroOS {
    suspend operator fun invoke(
        nroOS: String,
        flowApp: FlowApp = FlowApp.HEADER_INITIAL
    ): Result<Boolean>
}

class ISetNroOS @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): SetNroOS {

    override suspend fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): Result<Boolean> {
        try {
            val resultSetNroOSHeader = motoMecRepository.setNroOSHeader(
                nroOS.toInt()
            )
            resultSetNroOSHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if (flowApp == FlowApp.HEADER_INITIAL) return resultSetNroOSHeader
            val resultSetNroOSNote = motoMecRepository.setNroOSNote(
                nroOS.toInt()
            )
            resultSetNroOSNote.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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