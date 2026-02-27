package br.com.usinasantafe.cmm.infra.datasource.retrofit.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMechanicRetrofitModelInput

interface ItemOSMechanicRetrofitDatasource {
    suspend fun listByIdEquipAndNroOS(token: String, nroOS: Int, idEquip: Int): Result<List<ItemOSMechanicRetrofitModelInput>>

}