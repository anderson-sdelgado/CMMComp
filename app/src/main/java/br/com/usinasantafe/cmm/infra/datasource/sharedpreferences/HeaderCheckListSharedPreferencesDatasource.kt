package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.EmptyResult

interface HeaderCheckListSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderCheckListSharedPreferencesModel>
    suspend fun save(model: HeaderCheckListSharedPreferencesModel): EmptyResult
    suspend fun clean(): Result<Boolean>
    suspend fun hasOpen(): Result<Boolean>
}