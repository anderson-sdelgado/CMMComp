package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMechanicRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMechanicRetrofitModelOutput
import br.com.usinasantafe.cmm.lib.WEB_ITEM_OS_MECHANIC_BY_NRO_OS_AND_ID_EQUIP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ItemOSMechanicApi {

    @POST(WEB_ITEM_OS_MECHANIC_BY_NRO_OS_AND_ID_EQUIP)
    suspend fun listByIdEquipAndNroOS(
        @Header("Authorization") auth: String,
        @Body model: ItemOSMechanicRetrofitModelOutput
    ): Response<List<ItemOSMechanicRetrofitModelInput>>

}