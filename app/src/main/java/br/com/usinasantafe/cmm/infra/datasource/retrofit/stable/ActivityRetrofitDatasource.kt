package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel

interface ActivityRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ActivityRetrofitModel>>
}