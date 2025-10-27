package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemMenuPMMRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuPMMRetrofitModel
import javax.inject.Inject

class IItemMenuPMMRetrofitDatasource @Inject constructor(

): ItemMenuPMMRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<ItemMenuPMMRetrofitModel>> {
        TODO("Not yet implemented")
    }
}