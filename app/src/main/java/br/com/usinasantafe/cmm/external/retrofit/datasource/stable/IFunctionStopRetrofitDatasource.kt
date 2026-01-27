package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.FunctionStopApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionStopRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionStopRetrofitDatasource @Inject constructor(
    private val functionStopApi: FunctionStopApi
): FunctionStopRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<FunctionStopRetrofitModel>> =
        result(getClassAndMethod()) {
            functionStopApi.all(token).body()!!
        }

}