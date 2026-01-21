package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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
        try {
            if (flowApp == FlowApp.TRANSHIPMENT) {
                val resultGetNroOS = motoMecRepository.getNroOSHeader()
                resultGetNroOS.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                val nroOS = resultGetNroOS.getOrNull()!!
                val resultGetIdActivity = motoMecRepository.getIdActivityHeader()
                resultGetIdActivity.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                val idActivity = resultGetIdActivity.getOrNull()!!

                val resultSetNroOS = motoMecRepository.setNroOSNote(nroOS)
                resultSetNroOS.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                val resultSetIdActivity = motoMecRepository.setIdActivityNote(idActivity)
                resultSetIdActivity.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
            }

            val resultSetNroTranshipment = motoMecRepository.setNroEquipTranshipmentNote(nroEquipTranshipment.toLong())
            resultSetNroTranshipment.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }

            val resultIdHeader = motoMecRepository.getIdByHeaderOpen()
            resultIdHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idHeader = resultIdHeader.getOrNull()!!
            val result = motoMecRepository.saveNote(idHeader)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}