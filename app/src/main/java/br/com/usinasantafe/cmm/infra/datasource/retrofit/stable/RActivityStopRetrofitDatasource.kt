package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RActivityStopRetrofitModel

interface RActivityStopRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<RActivityStopRetrofitModel>>
}
