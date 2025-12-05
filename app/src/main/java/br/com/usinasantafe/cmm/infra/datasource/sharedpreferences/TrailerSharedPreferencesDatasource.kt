package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerSharedPreferencesModel

interface TrailerSharedPreferencesDatasource {
    suspend fun add(model: TrailerSharedPreferencesModel): Result<Boolean>
    suspend fun clean(): Result<Boolean>
    suspend fun has(): Result<Boolean>
    suspend fun list(): Result<List<TrailerSharedPreferencesModel>>
}