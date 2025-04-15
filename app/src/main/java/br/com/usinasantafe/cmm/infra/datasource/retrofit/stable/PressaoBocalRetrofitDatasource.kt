package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressaoBocalRetrofitModel

interface PressaoBocalRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<PressaoBocalRetrofitModel>>
}
