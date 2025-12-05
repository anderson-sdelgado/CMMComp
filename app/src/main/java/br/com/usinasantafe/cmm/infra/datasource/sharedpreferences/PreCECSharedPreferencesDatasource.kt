package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.PreCECSharedPreferencesModel
import java.util.Date

interface PreCECSharedPreferencesDatasource {
    suspend fun get(): Result<PreCECSharedPreferencesModel>
    suspend fun setDateExitMill(date: Date): Result<Boolean>
    suspend fun setDateFieldArrival(date: Date): Result<Boolean>
    suspend fun setDateExitField(date: Date): Result<Boolean>
}