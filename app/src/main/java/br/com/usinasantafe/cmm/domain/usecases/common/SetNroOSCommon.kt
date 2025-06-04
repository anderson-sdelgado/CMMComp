package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.NoteMotoMecRepository
import br.com.usinasantafe.cmm.utils.FlowApp
import javax.inject.Inject

interface SetNroOSCommon {
    suspend operator fun invoke(
        nroOS: String,
        flowApp: FlowApp = FlowApp.HEADER_DEFAULT
    ): Result<Boolean>
}

class ISetNroOSCommon @Inject constructor(
    private val headerMotoMecRepository: HeaderMotoMecRepository,
    private val noteMotoMecRepository: NoteMotoMecRepository
): SetNroOSCommon {

    override suspend fun invoke(
        nroOS: String,
        flowApp: FlowApp
    ): Result<Boolean> {
        try {
            val resultSetNroOSHeader = headerMotoMecRepository.setNroOS(
                nroOS.toInt()
            )
            if (resultSetNroOSHeader.isFailure) {
                val e = resultSetNroOSHeader.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetNroOSCommon",
                    message = e.message,
                    cause = e.cause
                )
            }
            if (flowApp == FlowApp.HEADER_DEFAULT) return resultSetNroOSHeader
            val resultSetNroOSNote = noteMotoMecRepository.setNroOS(
                nroOS.toInt()
            )
            if (resultSetNroOSNote.isFailure) {
                val e = resultSetNroOSNote.exceptionOrNull()!!
                return resultFailure(
                    context = "ISetNroOSCommon",
                    message = e.message,
                    cause = e.cause
                )
            }
            return resultSetNroOSNote
        } catch (e: Exception) {
            return resultFailure(
                context = "ISetNroOSCommon",
                message = "-",
                cause = e
            )
        }
    }

}