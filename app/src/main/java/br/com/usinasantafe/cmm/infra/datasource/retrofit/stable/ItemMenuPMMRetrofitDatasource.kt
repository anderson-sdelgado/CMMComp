package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuPMMRetrofitModel

interface ItemMenuPMMRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ItemMenuPMMRetrofitModel>>
}