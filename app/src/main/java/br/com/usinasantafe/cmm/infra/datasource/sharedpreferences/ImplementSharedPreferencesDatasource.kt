package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ImplementSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ImplementSharedPreferencesDatasource {
    suspend fun add(model: ImplementSharedPreferencesModel): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun list(): Result<List<ImplementSharedPreferencesModel>>

}