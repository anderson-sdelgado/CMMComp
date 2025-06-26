package br.com.usinasantafe.cmm.infra.datasource.retrofit.variable

import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelOutput

interface MotoMecRetrofitDatasource {
    suspend fun send(
        token: String,
        modelList: List<HeaderMotoMecRetrofitModelOutput>
    ): Result<List<HeaderMotoMecRetrofitModelInput>>
}