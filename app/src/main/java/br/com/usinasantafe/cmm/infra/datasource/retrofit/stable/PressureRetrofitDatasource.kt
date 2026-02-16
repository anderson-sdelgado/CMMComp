package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressureRetrofitModel

interface PressureRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<PressureRetrofitModel>>
}