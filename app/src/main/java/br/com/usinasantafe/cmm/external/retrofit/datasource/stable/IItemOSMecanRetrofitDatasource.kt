package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemOSMecanApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemOSMecanRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMecanRetrofitModel
import javax.inject.Inject

class IItemOSMecanRetrofitDatasource @Inject constructor(
    private val itemOSMecanApi: ItemOSMecanApi
) : ItemOSMecanRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ItemOSMecanRetrofitModel>> {
        try {
            val response = itemOSMecanApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IItemOSMecanRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
