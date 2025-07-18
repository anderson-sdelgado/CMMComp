package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.provider.OSApiDefault
import br.com.usinasantafe.cmm.di.provider.OSApiShortTimeout
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IOSRetrofitDatasource @Inject constructor(
    @OSApiDefault private val osApiDefault: OSApi,
    @OSApiShortTimeout private val osApiShortTimeout: OSApi
) : OSRetrofitDatasource {

    override suspend fun recoverAll(
        token: String
    ): Result<List<OSRetrofitModel>> {
        try {
            val response = osApiDefault.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getListByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<OSRetrofitModel>> {
        try {
            val response = osApiShortTimeout.getListByNroOS(
                auth = token,
                nroOS = nroOS
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
