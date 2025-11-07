package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuRetrofitModel

interface ItemMenuRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ItemMenuRetrofitModel>>
}