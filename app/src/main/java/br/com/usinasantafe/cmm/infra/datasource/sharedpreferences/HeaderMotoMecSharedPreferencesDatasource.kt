package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel

interface HeaderMotoMecSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderMotoMecSharedPreferencesModel>
    suspend fun save(model: HeaderMotoMecSharedPreferencesModel): Result<Boolean>
    suspend fun setRegOperator(regOperator: Int): Result<Boolean>
    suspend fun setIdEquip(idEquip: Int): Result<Boolean>
    suspend fun setIdTurn(idTurn: Int): Result<Boolean>
    suspend fun setNroOS(nroOS: Int): Result<Boolean>
    suspend fun setIdActivity(idActivity: Int): Result<Boolean>
    suspend fun getNroOS(): Result<Int>
    suspend fun getIdEquip(): Result<Int>
    suspend fun setMeasureInitial(measure: Double): Result<Boolean>
}