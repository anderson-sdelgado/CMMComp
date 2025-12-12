package br.com.usinasantafe.cmm.external.retrofit.api.stable

import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipMainRetrofitModel

import br.com.usinasantafe.cmm.lib.WEB_ALL_EQUIP
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface EquipApi {

    @POST(WEB_ALL_EQUIP)
    suspend fun all(
        @Header("Authorization") auth: String
    ): Response<List<EquipMainRetrofitModel>>

}