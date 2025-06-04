package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel

interface NoteMotoMecSharedPreferencesDatasource {
    suspend fun get(): Result<NoteMotoMecSharedPreferencesModel>
    suspend fun save(model: NoteMotoMecSharedPreferencesModel): Result<Boolean>
    suspend fun setNroOS(nroOS: Int): Result<Boolean>
    suspend fun setIdActivity(id: Int): Result<Boolean>
}