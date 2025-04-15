package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PropriedadeRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_PROPRIEDADE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PropriedadeApi {

    @GET(WEB_ALL_PROPRIEDADE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<PropriedadeRetrofitModel>>

}
