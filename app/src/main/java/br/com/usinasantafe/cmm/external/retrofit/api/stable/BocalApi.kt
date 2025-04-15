package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.BocalRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_BOCAL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface BocalApi {

    @GET(WEB_ALL_BOCAL)
    suspend fun all(@Header("Authorization") auth: String): Response<List<BocalRetrofitModel>>

}
