package br.com.usinasantafe.cmm.external.retrofit.api.variable

import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelOutput
import br.com.usinasantafe.cmm.lib.WEB_SAVE_CHECK_LIST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CheckListApi {

    @POST(WEB_SAVE_CHECK_LIST)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body modelList: List<HeaderCheckListRetrofitModelOutput>
    ): Response<List<HeaderCheckListRetrofitModelInput>>
}