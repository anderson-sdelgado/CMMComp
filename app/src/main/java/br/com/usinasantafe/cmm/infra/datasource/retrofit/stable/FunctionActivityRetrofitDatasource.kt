package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionActivityRetrofitModel

interface FunctionActivityRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<FunctionActivityRetrofitModel>>
}