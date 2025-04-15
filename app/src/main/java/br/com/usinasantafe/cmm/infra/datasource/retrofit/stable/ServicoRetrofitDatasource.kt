package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServicoRetrofitModel

interface ServicoRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ServicoRetrofitModel>>
}
