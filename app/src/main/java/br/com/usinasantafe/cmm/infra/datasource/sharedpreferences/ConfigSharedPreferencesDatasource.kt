package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel

interface ConfigSharedPreferencesDatasource {
    suspend fun get(): Result<ConfigSharedPreferencesModel>
    suspend fun has(): Result<Boolean>
    suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean>
}