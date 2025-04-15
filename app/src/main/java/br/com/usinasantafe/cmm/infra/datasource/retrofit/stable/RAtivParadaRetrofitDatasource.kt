package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RAtivParadaRetrofitModel

interface RAtivParadaRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<RAtivParadaRetrofitModel>>
}
