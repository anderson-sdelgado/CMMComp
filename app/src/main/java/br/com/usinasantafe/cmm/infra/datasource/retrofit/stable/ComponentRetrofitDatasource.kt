package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ComponentRetrofitModel

interface ComponentRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ComponentRetrofitModel>>
}