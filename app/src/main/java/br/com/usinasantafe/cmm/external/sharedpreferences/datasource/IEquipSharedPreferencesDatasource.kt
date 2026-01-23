package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_EQUIP
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject

class IEquipSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): EquipSharedPreferencesDatasource {

    fun get(): Result<EquipSharedPreferencesModel?> {
        try {
            val string = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_EQUIP,
                null
            )
            if(string.isNullOrEmpty())
                return Result.success(null)
            return Result.success(
                Gson().fromJson(
                    string,
                    EquipSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: EquipSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_EQUIP,
                    Gson().toJson(model)
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getId(): Result<Int> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.id)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getNro(): Result<Long> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.nro)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getDescr(): Result<String> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success("${model.nro} - ${model.descrClass}")
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getCodTurnEquip(): Result<Int> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.codTurnEquip)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getHourMeter(): Result<Double> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.hourMeter)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun updateHourMeter(hourMeter: Double): EmptyResult {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.hourMeter = hourMeter
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(Unit)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getTypeEquip(): Result<TypeEquip> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.typeEquip)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdCheckList(): Result<Int> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.idCheckList)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getFlagMechanic(): Result<Boolean> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.flagMechanic)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getFlagTire(): Result<Boolean> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.flagTire)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}