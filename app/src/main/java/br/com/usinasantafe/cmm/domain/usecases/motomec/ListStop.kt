package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.RActivityStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.StopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.StopScreenModel
import br.com.usinasantafe.cmm.utils.call
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

    override suspend fun invoke(): Result<List<StopScreenModel>> =
        call(getClassAndMethod()) {
            val idActivity = motoMecRepository.getIdActivityNote().getOrThrow()
            val list = rActivityStopRepository.listByIdActivity(idActivity).getOrThrow()
            val idList = list.map { it.idStop }
            val stopList = stopRepository.listByIdList(idList).getOrThrow()
            stopList.map {
                StopScreenModel(
                    id = it.idStop,
                    descr = "${it.codStop} - ${it.descrStop}",
                )
            }
        }

}