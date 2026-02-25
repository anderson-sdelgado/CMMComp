package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.ComponentApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ComponentRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ComponentRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IComponentRetrofitDatasource @Inject constructor(
    private val componentApi: ComponentApi
): ComponentRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<ComponentRetrofitModel>> =
        result(getClassAndMethod()) {
            componentApi.all(token).body()!!
        }
}