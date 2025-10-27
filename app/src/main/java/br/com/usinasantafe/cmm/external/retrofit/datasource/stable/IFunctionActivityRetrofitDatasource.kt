package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionActivityRetrofitModel
import javax.inject.Inject

class IFunctionActivityRetrofitDatasource @Inject constructor(

): FunctionActivityRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<FunctionActivityRetrofitModel>> {
        TODO("Not yet implemented")
    }

}