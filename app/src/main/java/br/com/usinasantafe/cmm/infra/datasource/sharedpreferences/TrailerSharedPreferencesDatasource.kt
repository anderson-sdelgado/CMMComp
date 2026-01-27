package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface TrailerSharedPreferencesDatasource {
    suspend fun add(model: TrailerSharedPreferencesModel): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun has(): Result<Boolean>
    suspend fun list(): Result<List<TrailerSharedPreferencesModel>>
}