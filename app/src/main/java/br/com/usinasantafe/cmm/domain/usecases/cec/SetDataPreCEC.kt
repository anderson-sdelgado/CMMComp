package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.StatusPreCEC
import javax.inject.Inject

interface SetDataPreCEC {
    suspend operator fun invoke(item: ItemMenuModel): Result<StatusPreCEC>
}

class ISetDataPreCEC @Inject constructor(
): SetDataPreCEC {

    override suspend fun invoke(item: ItemMenuModel): Result<StatusPreCEC> {
        TODO("Not yet implemented")
    }

}