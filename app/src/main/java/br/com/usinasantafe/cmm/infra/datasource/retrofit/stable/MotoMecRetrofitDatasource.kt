package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.MotoMecRetrofitModel

interface MotoMecRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<MotoMecRetrofitModel>>
}
