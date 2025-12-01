package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RItemMenuStopRetrofitModel

interface RItemMenuStopRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<RItemMenuStopRetrofitModel>>
}