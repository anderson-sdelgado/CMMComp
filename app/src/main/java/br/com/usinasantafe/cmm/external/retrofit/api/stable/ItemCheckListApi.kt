package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_ITEM_CHECKLIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ItemCheckListApi {

    @GET(WEB_ALL_ITEM_CHECKLIST)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ItemCheckListRetrofitModel>>

}
