package br.com.usinasantafe.cmm.external.retrofit.api.variable

import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelOutput
import br.com.usinasantafe.cmm.lib.WEB_SAVE_MOTO_MEC
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MotoMecApi {

    @POST(WEB_SAVE_MOTO_MEC)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body modelList: List<HeaderMotoMecRetrofitModelOutput>
    ): Response<List<HeaderMotoMecRetrofitModelInput>>
}