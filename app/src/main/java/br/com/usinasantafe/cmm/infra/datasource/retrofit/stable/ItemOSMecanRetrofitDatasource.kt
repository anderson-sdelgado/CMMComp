package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMecanRetrofitModel

interface ItemOSMecanRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ItemOSMecanRetrofitModel>>
}
