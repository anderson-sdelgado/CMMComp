package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.provider.OSApiDefault
import br.com.usinasantafe.cmm.di.provider.OSApiShortTimeout
import br.com.usinasantafe.cmm.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IOSRetrofitDatasource @Inject constructor(
    @OSApiDefault private val osApiDefault: OSApi,
    @OSApiShortTimeout private val osApiShortTimeout: OSApi
) : OSRetrofitDatasource {

    override suspend fun listAll(
        token: String
    ): Result<List<OSRetrofitModel>> =
        result(getClassAndMethod()) {
            osApiDefault.all(token).body()!!
        }

    override suspend fun getListByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<OSRetrofitModel>> =
        result(getClassAndMethod()) {
            osApiShortTimeout.getListByNroOS(token, nroOS).body()!!
        }
}
