package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemValueOSScreenModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListCollection {
    suspend operator fun invoke(): Result<List<ItemValueOSScreenModel>>
}

class IListCollection @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private var fertigationRepository: FertigationRepository
): ListCollection {

    override suspend fun invoke(): Result<List<ItemValueOSScreenModel>> =
        call(getClassAndMethod()) {
            val id = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val list = fertigationRepository.listCollectionByIdHeader(id).getOrThrow()
            list.map {
                ItemValueOSScreenModel(
                    id = it.id,
                    nroOS = it.nroOS,
                    value = it.value
                )
            }
        }

}