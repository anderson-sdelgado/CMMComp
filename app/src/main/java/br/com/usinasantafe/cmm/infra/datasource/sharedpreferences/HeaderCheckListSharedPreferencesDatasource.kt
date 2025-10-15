package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel

interface HeaderCheckListSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderCheckListSharedPreferencesModel>
    suspend fun save(model: HeaderCheckListSharedPreferencesModel): Result<Boolean>
    suspend fun clean(): Result<Boolean>
}