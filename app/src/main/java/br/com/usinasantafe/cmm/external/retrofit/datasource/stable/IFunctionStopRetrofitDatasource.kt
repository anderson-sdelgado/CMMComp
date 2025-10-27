package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionStopRetrofitModel
import javax.inject.Inject

class IFunctionStopRetrofitDatasource @Inject constructor(

): FunctionStopRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<FunctionStopRetrofitModel>> {
        TODO("Not yet implemented")
    }
}