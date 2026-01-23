package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip

interface HeaderMotoMecSharedPreferencesDatasource {
    suspend fun get(): Result<HeaderMotoMecSharedPreferencesModel>
    suspend fun save(model: HeaderMotoMecSharedPreferencesModel): EmptyResult
    suspend fun setRegOperator(regOperator: Int): EmptyResult
    suspend fun setDataEquip(
        idEquip: Int,
        typeEquip: TypeEquip
    ): EmptyResult
    suspend fun getTypeEquip(): Result<TypeEquip>
    suspend fun setIdTurn(idTurn: Int): EmptyResult
    suspend fun setNroOS(nroOS: Int): EmptyResult
    suspend fun setIdActivity(idActivity: Int): EmptyResult
    suspend fun getNroOS(): Result<Int>
    suspend fun getIdEquip(): Result<Int>
    suspend fun setHourMeter(hourMeter: Double): EmptyResult
    suspend fun clean(): Result<Boolean>
    suspend fun setStatusCon(status: Boolean): EmptyResult
    suspend fun getIdActivity(): Result<Int>
    suspend fun getStatusCon(): Result<Boolean>
    suspend fun getId(): Result<Int>
    suspend fun setId(id: Int): EmptyResult
    suspend fun setIdEquipMotorPump(idEquip: Int): EmptyResult
}