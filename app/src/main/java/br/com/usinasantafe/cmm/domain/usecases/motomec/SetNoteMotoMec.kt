package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNoteMotoMec {
    suspend operator fun invoke(item: ItemMenuModel): Result<Boolean>
}

class ISetNoteMotoMec @Inject constructor(
    private val rItemMenuStopRepository: RItemMenuStopRepository,
    private val motoMecRepository: MotoMecRepository,
): SetNoteMotoMec {

    override suspend fun invoke(item: ItemMenuModel): Result<Boolean> {
        try {
            val resultGetIdStop = rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                idFunction = item.function.first,
                idApp = item.app.first
            )
            resultGetIdStop.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultGetIdHeader = motoMecRepository.getIdByHeaderOpen()
            resultGetIdHeader.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultGetNroOS = motoMecRepository.getNroOSHeader()
            resultGetNroOS.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultGetIdActivity = motoMecRepository.getIdActivityHeader()
            resultGetIdActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultSetNroOS = motoMecRepository.setNroOSNote(resultGetNroOS.getOrNull()!!)
            resultSetNroOS.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultSetIdActivity = motoMecRepository.setIdActivityNote(resultGetIdActivity.getOrNull()!!)
            resultSetIdActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            if (resultGetIdStop.getOrNull() != null) {
                val resultSetIdStop = motoMecRepository.setIdStop(resultGetIdStop.getOrNull()!!)
                resultSetIdStop.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
            }
            val resultSave = motoMecRepository.saveNote(resultGetIdHeader.getOrNull()!!)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return resultSave
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}