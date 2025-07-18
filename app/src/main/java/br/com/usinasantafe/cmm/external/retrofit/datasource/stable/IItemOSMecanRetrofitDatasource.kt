package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemOSMecanApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemOSMecanRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMecanRetrofitModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemOSMecanRetrofitDatasource @Inject constructor(
    private val itemOSMecanApi: ItemOSMecanApi
) : ItemOSMecanRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ItemOSMecanRetrofitModel>> {
        try {
            val response = itemOSMecanApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
