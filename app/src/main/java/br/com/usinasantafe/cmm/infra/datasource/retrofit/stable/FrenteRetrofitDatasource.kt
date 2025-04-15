package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FrenteRetrofitModel

interface FrenteRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<FrenteRetrofitModel>>
}
