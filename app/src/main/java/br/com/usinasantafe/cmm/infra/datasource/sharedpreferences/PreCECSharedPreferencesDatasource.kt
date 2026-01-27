package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.PreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import java.util.Date

interface PreCECSharedPreferencesDatasource {
    suspend fun get(): Result<PreCECSharedPreferencesModel>
    suspend fun setDateExitMill(date: Date): EmptyResult
    suspend fun setDateFieldArrival(date: Date): EmptyResult
    suspend fun setDateExitField(date: Date): EmptyResult
}