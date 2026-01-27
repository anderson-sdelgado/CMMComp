package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface HeaderCheckListSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderCheckListSharedPreferencesModel>
    suspend fun save(model: HeaderCheckListSharedPreferencesModel): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun hasOpen(): Result<Boolean>
}