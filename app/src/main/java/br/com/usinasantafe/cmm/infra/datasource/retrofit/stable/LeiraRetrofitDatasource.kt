package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.LeiraRetrofitModel

interface LeiraRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<LeiraRetrofitModel>>
}
