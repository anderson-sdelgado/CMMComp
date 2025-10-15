package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ComponenteRetrofitModel

interface ComponenteRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ComponenteRetrofitModel>>
}
