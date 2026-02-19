package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.presenter.model.ItemValueOSScreenModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListCollection {
    suspend operator fun invoke(): Result<List<ItemValueOSScreenModel>>
}

class IListCollection @Inject constructor(
): ListCollection {

    override suspend fun invoke(): Result<List<ItemValueOSScreenModel>> =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}