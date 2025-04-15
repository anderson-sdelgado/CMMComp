package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSAtivRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_R_OS_ATIV
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ROSAtivApi {

    @GET(WEB_ALL_R_OS_ATIV)
    suspend fun all(@Header("Authorization") auth: String): Response<List<ROSAtivRetrofitModel>>

}
