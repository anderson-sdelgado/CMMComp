package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.domain.usecases.cec.SetIdRelease
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderPreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import java.util.Date

interface HeaderPreCECSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderPreCECSharedPreferencesModel>
    suspend fun setDateExitMill(date: Date): EmptyResult
    suspend fun setDateFieldArrival(date: Date): EmptyResult
    suspend fun setDateExitField(date: Date): EmptyResult
    suspend fun setData(
        nroEquip: Long,
        regColab: Long,
        nroTurn: Int,
    ): EmptyResult
    suspend fun setIdRelease(idRelease: Int): EmptyResult
}