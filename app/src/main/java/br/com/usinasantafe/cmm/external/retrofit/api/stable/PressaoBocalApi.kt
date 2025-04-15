package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressaoBocalRetrofitModel
import br.com.usinasantafe.cmm.utils.WEB_ALL_PRESSAO_BOCAL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PressaoBocalApi {

    @GET(WEB_ALL_PRESSAO_BOCAL)
    suspend fun all(@Header("Authorization") auth: String): Response<List<PressaoBocalRetrofitModel>>

}
