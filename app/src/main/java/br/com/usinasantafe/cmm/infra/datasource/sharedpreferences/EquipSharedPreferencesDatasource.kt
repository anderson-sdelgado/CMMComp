package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquip

interface EquipSharedPreferencesDatasource {
    suspend fun save(model: EquipSharedPreferencesModel): Result<Boolean>
    suspend fun getId(): Result<Int>
    suspend fun getNro(): Result<Long>
    suspend fun getDescr(): Result<String>
    suspend fun getCodTurnEquip(): Result<Int>
    suspend fun getHourMeter(): Result<Double>
    suspend fun updateHourMeter(hourMeter: Double, ): Result<Boolean>
    suspend fun getTypeEquip(): Result<TypeEquip>
    suspend fun getIdCheckList(): Result<Int>
    suspend fun getFlagMechanic(): Result<Boolean>
    suspend fun getFlagTire(): Result<Boolean>
}