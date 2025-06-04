package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel

interface StopRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<StopRetrofitModel>>
}
