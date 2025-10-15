package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressaoBocalRetrofitModel

interface PressaoBocalRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<PressaoBocalRetrofitModel>>
}
