package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServiceRetrofitModel

interface ServiceRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ServiceRetrofitModel>>
}