package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.StopScreenModel
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
            val resultGetIdActivity = motoMecRepository.getIdActivity()
            if (resultGetIdActivity.isFailure) {
                val e = resultGetIdActivity.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetStopList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idActivity = resultGetIdActivity.getOrNull()!!
            val resultListByIdActivity = rActivityStopRepository.listByIdActivity(idActivity)
            if (resultListByIdActivity.isFailure) {
                val e = resultListByIdActivity.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetStopList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val list = resultListByIdActivity.getOrNull()!!
            val idList = list.map { it.idStop }
            val resultListByIdList = stopRepository.listByIdList(idList)
            if (resultListByIdList.isFailure) {
                val e = resultListByIdList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetStopList",
                    message = e.message,
                    cause = e.cause
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
                context = "IGetStopList",
                message = "-",
                cause = e
            )
        }
    }

}