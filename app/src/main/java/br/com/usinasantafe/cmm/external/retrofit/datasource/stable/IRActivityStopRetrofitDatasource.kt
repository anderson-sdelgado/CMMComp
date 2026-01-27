package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.RActivityStopApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RActivityStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RActivityStopRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IRActivityStopRetrofitDatasource @Inject constructor(
    private val rActivityStopApi: RActivityStopApi
) : RActivityStopRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<RActivityStopRetrofitModel>> =
        result(getClassAndMethod()) {
            rActivityStopApi.all(token).body()!!
        }
}
