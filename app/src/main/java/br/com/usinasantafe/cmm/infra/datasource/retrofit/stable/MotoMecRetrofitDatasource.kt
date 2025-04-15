package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.MotoMecRetrofitModel

interface MotoMecRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<MotoMecRetrofitModel>>
}
