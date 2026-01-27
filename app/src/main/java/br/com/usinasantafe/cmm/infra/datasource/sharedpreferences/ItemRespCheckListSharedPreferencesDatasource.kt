package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemRespCheckListSharedPreferencesDatasource {
    suspend fun add(model: RespItemCheckListSharedPreferencesModel): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun list(): Result<List<RespItemCheckListSharedPreferencesModel>>
    suspend fun delLast(): EmptyResult
}