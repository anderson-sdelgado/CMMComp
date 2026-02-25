package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.ServiceApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ServiceRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServiceRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IServiceRetrofitDatasource  @Inject constructor(
    private val serviceApi: ServiceApi
): ServiceRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<ServiceRetrofitModel>> =
        result(getClassAndMethod()) {
            serviceApi.all(token).body()!!
        }

}