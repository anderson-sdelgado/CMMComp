package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.CheckUpdateCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel

interface ItemCheckListRetrofitDatasource {
    suspend fun listByNroEquip(token: String, nroEquip: Long): Result<List<ItemCheckListRetrofitModel>>
    suspend fun checkUpdateByNroEquip(token: String, nroEquip: Long): Result<CheckUpdateCheckListRetrofitModel>
}
