package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.AtividadeRetrofitModel

interface AtividadeRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<AtividadeRetrofitModel>>
}