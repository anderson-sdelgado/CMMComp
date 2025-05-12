package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipRetrofitModel

import br.com.usinasantafe.cmm.utils.WEB_ALL_EQUIP
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EquipApi {

    @POST(WEB_ALL_EQUIP)
    suspend fun all(
        @Header("Authorization") auth: String,
        @Body idEquip: Int
    ): Response<List<EquipRetrofitModel>>

}