package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel

interface RespItemCheckListSharedPreferencesDatasource {
    suspend fun save(model: RespItemCheckListSharedPreferencesModel): Result<Boolean>
    suspend fun clean(): Result<Boolean>
    suspend fun list(): Result<List<RespItemCheckListSharedPreferencesModel>>
    suspend fun delLast(): Result<Boolean>
}