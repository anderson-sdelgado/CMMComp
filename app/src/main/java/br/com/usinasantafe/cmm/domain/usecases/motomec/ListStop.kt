package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.StopScreenModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListStop {
    suspend operator fun invoke(): Result<List<StopScreenModel>>
}

class IListStop @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val rActivityStopRepository: RActivityStopRepository,
    private val stopRepository: StopRepository
): ListStop {

    override suspend fun invoke(): Result<List<StopScreenModel>> {
        try {
            val resultGetIdActivity = motoMecRepository.getIdActivityNote()
            resultGetIdActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idActivity = resultGetIdActivity.getOrNull()!!
            val resultListByIdActivity = rActivityStopRepository.listByIdActivity(idActivity)
            resultListByIdActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val list = resultListByIdActivity.getOrNull()!!
            val idList = list.map { it.idStop }
            val resultListByIdList = stopRepository.listByIdList(idList)
            resultListByIdList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val stopList = resultListByIdList.getOrNull()!!
            val stopScreenModelList = stopList.map {
                StopScreenModel(
                    id = it.idStop,
                    descr = "${it.codStop} - ${it.descrStop}",
                )
            }
            return Result.success(stopScreenModelList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}