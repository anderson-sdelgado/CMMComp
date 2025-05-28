package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSActivityRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_R_OS_ATIV
import br.com.usinasantafe.cmm.utils.WEB_GET_R_OS_ACTIVITY_LIST_BY_NRO_OS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ROSActivityApi {

    @GET(WEB_ALL_R_OS_ATIV)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ROSActivityRetrofitModel>>

    @POST(WEB_GET_R_OS_ACTIVITY_LIST_BY_NRO_OS)
    suspend fun getListByNroOS(
        @Header("Authorization") auth: String,
        @Body nroOS: Int
    ): Response<List<ROSActivityRetrofitModel>>

}
