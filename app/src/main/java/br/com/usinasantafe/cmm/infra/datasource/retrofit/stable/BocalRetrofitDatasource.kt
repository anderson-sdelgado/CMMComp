package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.BocalRetrofitModel

interface BocalRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<BocalRetrofitModel>>
}
