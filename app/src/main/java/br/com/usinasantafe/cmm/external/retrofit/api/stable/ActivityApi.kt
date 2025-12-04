package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import br.com.usinasantafe.cmm.lib.WEB_ALL_ACTIVITY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ActivityApi {

    @GET(WEB_ALL_ACTIVITY)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ActivityRetrofitModel>>

}