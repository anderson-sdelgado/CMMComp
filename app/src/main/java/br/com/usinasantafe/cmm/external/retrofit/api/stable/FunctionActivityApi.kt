package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionActivityRetrofitModel
import br.com.usinasantafe.cmm.lib.WEB_ALL_FUNCTION_ACTIVITY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FunctionActivityApi {

    @GET(WEB_ALL_FUNCTION_ACTIVITY)
    suspend fun all(@Header("Authorization") auth: String): Response<List<FunctionActivityRetrofitModel>>

}