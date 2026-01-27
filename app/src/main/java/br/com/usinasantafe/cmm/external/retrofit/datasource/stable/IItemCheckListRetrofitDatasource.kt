package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemCheckListApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemCheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.CheckUpdateCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemCheckListRetrofitDatasource @Inject constructor(
    private val itemCheckListApi: ItemCheckListApi
) : ItemCheckListRetrofitDatasource {

    override suspend fun listByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<List<ItemCheckListRetrofitModel>> =
        result(getClassAndMethod()) {
            itemCheckListApi.listBryNroEquip(token, nroEquip).body()!!
        }

    override suspend fun checkUpdateByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<CheckUpdateCheckListRetrofitModel> =
        result(getClassAndMethod()) {
            itemCheckListApi.checkByNroEquip(token, nroEquip).body()!!
        }

}
