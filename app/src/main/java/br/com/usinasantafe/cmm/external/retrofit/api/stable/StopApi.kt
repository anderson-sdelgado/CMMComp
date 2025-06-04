package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_STOP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface StopApi {

    @GET(WEB_ALL_STOP)
    suspend fun all(@Header("Authorization") auth: String): Response<List<StopRetrofitModel>>

}
