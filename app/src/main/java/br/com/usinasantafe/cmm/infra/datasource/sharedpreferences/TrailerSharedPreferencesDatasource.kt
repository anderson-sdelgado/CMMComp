package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.EmptyResult

interface TrailerSharedPreferencesDatasource {
    suspend fun add(model: TrailerSharedPreferencesModel): Result<Boolean>
    suspend fun clean(): EmptyResult
    suspend fun has(): Result<Boolean>
    suspend fun list(): Result<List<TrailerSharedPreferencesModel>>
}