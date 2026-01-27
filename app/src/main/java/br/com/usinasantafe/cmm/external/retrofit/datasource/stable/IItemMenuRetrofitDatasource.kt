package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemMenuApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemMenuRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemMenuRetrofitDatasource @Inject constructor(
    private val itemMenuApi: ItemMenuApi
): ItemMenuRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<ItemMenuRetrofitModel>> =
        result(getClassAndMethod()) {
            itemMenuApi.all(token).body()!!
        }
}