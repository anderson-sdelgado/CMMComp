package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.ROSActivityApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IROSActivityRetrofitDatasource @Inject constructor(
    private val rosActivityApi: ROSActivityApi
) : ROSActivityRetrofitDatasource {
    override suspend fun listAll(token: String): Result<List<ROSActivityRetrofitModel>> =
        result(getClassAndMethod()) {
            rosActivityApi.all(token).body()!!
        }

    override suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSActivityRetrofitModel>> =
        result(getClassAndMethod()) {
            rosActivityApi.getListByNroOS(token, nroOS).body()!!
        }
}
