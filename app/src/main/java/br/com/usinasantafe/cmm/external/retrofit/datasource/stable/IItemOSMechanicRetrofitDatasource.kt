package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemOSMechanicApi
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemOSMechanicRetrofitDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMechanicRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMechanicRetrofitModelOutput
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IItemOSMechanicRetrofitDatasource @Inject constructor(
    private val itemOSMechanicApi: ItemOSMechanicApi
): ItemOSMechanicRetrofitDatasource {

    override suspend fun listByIdEquipAndNroOS(
        token: String,
        nroOS: Int,
        idEquip: Int
    ): Result<List<ItemOSMechanicRetrofitModelInput>> =
        result(getClassAndMethod()) {
            val model = ItemOSMechanicRetrofitModelOutput(
                idEquip = idEquip,
                nroOS = nroOS
            )
            itemOSMechanicApi.listByIdEquipAndNroOS(token, model).body()!!
        }

}