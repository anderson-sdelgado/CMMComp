package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.ActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IActivityRetrofitDatasource @Inject constructor(
    private val activityApi: ActivityApi
) : ActivityRetrofitDatasource {

    override suspend fun listAll(
        token: String
    ): Result<List<ActivityRetrofitModel>> =
        result(getClassAndMethod()) {
            activityApi.all(token).body()!!
        }

}