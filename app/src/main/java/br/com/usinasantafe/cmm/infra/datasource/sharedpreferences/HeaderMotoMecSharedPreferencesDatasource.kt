package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip

interface HeaderMotoMecSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderMotoMecSharedPreferencesModel>
    suspend fun save(model: HeaderMotoMecSharedPreferencesModel): Result<Boolean>
    suspend fun setRegOperator(regOperator: Int): Result<Boolean>
    suspend fun setDataEquip(
        idEquip: Int,
        typeEquip: TypeEquip
    ): Result<Boolean>
    suspend fun getTypeEquip(): Result<TypeEquip>
    suspend fun setIdTurn(idTurn: Int): Result<Boolean>
    suspend fun setNroOS(nroOS: Int): Result<Boolean>
    suspend fun setIdActivity(idActivity: Int): EmptyResult
    suspend fun getNroOS(): Result<Int>
    suspend fun getIdEquip(): Result<Int>
    suspend fun setHourMeter(hourMeter: Double): EmptyResult
    suspend fun clean(): Result<Boolean>
    suspend fun setStatusCon(status: Boolean): Result<Boolean>
    suspend fun getIdActivity(): Result<Int>
    suspend fun getStatusCon(): Result<Boolean>
    suspend fun getId(): Result<Int>
    suspend fun setId(id: Int): Result<Boolean>
    suspend fun setIdEquipMotorPump(idEquip: Int): Result<Boolean>
}