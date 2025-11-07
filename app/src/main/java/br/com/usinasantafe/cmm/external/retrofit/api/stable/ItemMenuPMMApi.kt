package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_ITEM_MENU
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ItemMenuPMMApi {

    @GET(WEB_ALL_ITEM_MENU)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ItemMenuRetrofitModel>>

}