package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ParadaRetrofitModel

interface ParadaRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ParadaRetrofitModel>>
}
