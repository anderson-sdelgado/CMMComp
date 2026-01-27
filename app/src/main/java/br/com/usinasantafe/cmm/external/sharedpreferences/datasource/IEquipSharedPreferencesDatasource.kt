package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_EQUIP
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import javax.inject.Inject

class IEquipSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): EquipSharedPreferencesDatasource {

    suspend fun get(): Result<EquipSharedPreferencesModel?> =
        result(getClassAndMethod()) {
            val string = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_EQUIP,
                null
            )
            if(string.isNullOrEmpty())
                return@result null
            Gson().fromJson(
                string,
                EquipSharedPreferencesModel::class.java
            )
        }

    override suspend fun save(model: EquipSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_EQUIP,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun getId(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.id
        }

    override suspend fun getNro(): Result<Long> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.nro
        }

    override suspend fun getDescr(): Result<String> =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()!!
            "${model.nro} - ${model.descrClass}"
        }

    override suspend fun getCodTurnEquip(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.codTurnEquip
        }

    override suspend fun getHourMeter(): Result<Double> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.hourMeter
        }

    override suspend fun updateHourMeter(hourMeter: Double): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()!!
            model.hourMeter = hourMeter
            val resultSave = save(model).getOrThrow()
        }

    override suspend fun getTypeEquip(): Result<TypeEquip> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.typeEquip
        }

    override suspend fun getIdCheckList(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.idCheckList
        }

    override suspend fun getFlagMechanic(): Result<Boolean> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.flagMechanic
        }

    override suspend fun getFlagTire(): Result<Boolean> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.flagTire
        }

}