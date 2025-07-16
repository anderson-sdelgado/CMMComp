package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.CheckUpdateCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_ITEM_CHECKLIST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ItemCheckListApi {

    @GET(WEB_ALL_ITEM_CHECKLIST)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ItemCheckListRetrofitModel>>

    @POST(WEB_ALL_ITEM_CHECKLIST)
    suspend fun checkByNroEquip(
        @Header("Authorization") auth: String,
        @Body nroEquip: Long
    ): Response<CheckUpdateCheckListRetrofitModel>

}
