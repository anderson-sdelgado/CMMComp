package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RItemMenuStopRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_R_ITEM_MENU_STOP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RItemMenuStopApi {

    @GET(WEB_ALL_R_ITEM_MENU_STOP)
    suspend fun all(@Header("Authorization") auth: String): Response<List<RItemMenuStopRetrofitModel>>

}