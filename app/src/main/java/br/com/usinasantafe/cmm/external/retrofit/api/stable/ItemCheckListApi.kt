package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.CheckUpdateCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_CHECK_LIST_BY_NRO_EQUIP
import br.com.usinasantafe.cmm.utils.WEB_CHECK_CHECK_LIST_BY_NRO_EQUIP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ItemCheckListApi {

    @GET(WEB_ALL_CHECK_LIST_BY_NRO_EQUIP)
    suspend fun listBryNroEquip(
        @Header("Authorization") auth: String,
        @Body nroEquip: Long
    ): Response<List<ItemCheckListRetrofitModel>>

    @POST(WEB_CHECK_CHECK_LIST_BY_NRO_EQUIP)
    suspend fun checkByNroEquip(
        @Header("Authorization") auth: String,
        @Body nroEquip: Long
    ): Response<CheckUpdateCheckListRetrofitModel>

    @POST(WEB_ALL_ITEM_CHECKLIST)
    suspend fun checkByNroEquip(
        @Header("Authorization") auth: String,
        @Body nroEquip: Long
    ): Response<CheckUpdateCheckListRetrofitModel>

}
