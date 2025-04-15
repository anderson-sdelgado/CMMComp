package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel

interface ItemCheckListRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ItemCheckListRetrofitModel>>
}
