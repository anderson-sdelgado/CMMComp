package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionStopRetrofitModel

interface FunctionStopRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<FunctionStopRetrofitModel>>
}