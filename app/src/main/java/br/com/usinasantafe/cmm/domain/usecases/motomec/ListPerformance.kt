package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemPerformanceScreenModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListPerformance {
    suspend operator fun invoke(): Result<List<ItemPerformanceScreenModel>>
}

class IListPerformance @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
): ListPerformance {

    override suspend fun invoke(): Result<List<ItemPerformanceScreenModel>> =
        call(getClassAndMethod()) {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val list = motoMecRepository.listPerformanceByIdHeader(id).getOrThrow()
            list.map {
                ItemPerformanceScreenModel(
                    id = it.id,
                    nroOS = it.nroOS,
                    value = it.value
                )
            }

        }

}