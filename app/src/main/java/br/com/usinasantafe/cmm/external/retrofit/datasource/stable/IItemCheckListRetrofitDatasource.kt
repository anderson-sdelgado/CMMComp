package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemCheckListApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemCheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.CheckUpdateCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import javax.inject.Inject

class IItemCheckListRetrofitDatasource @Inject constructor(
    private val itemCheckListApi: ItemCheckListApi
) : ItemCheckListRetrofitDatasource {
    override suspend fun recoverAll(token: String): Result<List<ItemCheckListRetrofitModel>> {
        try {
            val response = itemCheckListApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IItemCheckListRetrofitDatasource.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkUpdateByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<CheckUpdateCheckListRetrofitModel> {
        try {
            val response = itemCheckListApi.checkByNroEquip(
                auth = token,
                nroEquip = nroEquip
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IItemCheckListRetrofitDatasource.checkUpdateByNroEquip",
                message = "-",
                cause = e
            )
        }
    }


}
