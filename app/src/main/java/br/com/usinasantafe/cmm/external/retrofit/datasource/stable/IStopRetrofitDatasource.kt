package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.StopApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.StopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IStopRetrofitDatasource @Inject constructor(
    private val stopApi: StopApi
) : StopRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<StopRetrofitModel>> =
        result(getClassAndMethod()) {
            stopApi.all(token).body()!!
        }
}
