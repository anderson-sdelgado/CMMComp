package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMecanRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_ITEM_OS_MECAN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ItemOSMecanApi {

    @GET(WEB_ALL_ITEM_OS_MECAN)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ItemOSMecanRetrofitModel>>

}
