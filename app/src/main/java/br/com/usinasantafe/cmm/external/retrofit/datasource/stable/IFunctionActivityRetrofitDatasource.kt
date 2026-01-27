package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.FunctionActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionActivityRetrofitDatasource @Inject constructor(
    private val functionActivityApi: FunctionActivityApi
): FunctionActivityRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<FunctionActivityRetrofitModel>> =
        result(getClassAndMethod()) {
            functionActivityApi.all(token).body()!!
        }

}