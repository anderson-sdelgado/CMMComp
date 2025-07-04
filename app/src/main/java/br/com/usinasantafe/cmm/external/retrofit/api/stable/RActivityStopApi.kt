package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RActivityStopRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_R_ACTIVITY_STOP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RActivityStopApi {

    @GET(WEB_ALL_R_ACTIVITY_STOP)
    suspend fun all(@Header("Authorization") auth: String): Response<List<RActivityStopRetrofitModel>>

}
