package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import javax.inject.Inject

interface SetNoteMotoMec {
    suspend operator fun invoke(item: ItemMenuModel): Result<Boolean>
}

class ISetNoteMotoMec @Inject constructor(
): SetNoteMotoMec {

    override suspend fun invoke(item: ItemMenuModel): Result<Boolean> {
        TODO("Not yet implemented")
    }

}