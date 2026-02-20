package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.presenter.model.ItemValueOSScreenModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListPerformance {
    suspend operator fun invoke(): Result<List<ItemValueOSScreenModel>>
}

class IListPerformance @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val performanceRepository: PerformanceRepository,
): ListPerformance {

    override suspend fun invoke(): Result<List<ItemValueOSScreenModel>> =
        call(getClassAndMethod()) {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val list = performanceRepository.listByIdHeader(id).getOrThrow()
            list.map {
                ItemValueOSScreenModel(
                    id = it.id,
                    nroOS = it.nroOS,
                    value = it.value
                )
            }
        }

}