package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.PreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.PreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_PRE_CEC
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject

class IPreCECSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreCECSharedPreferencesDatasource {

    override suspend fun get(): Result<PreCECSharedPreferencesModel> {
        try {
            val string = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_PRE_CEC,
                null
            )
            if(string.isNullOrEmpty())
                return Result.success(
                    PreCECSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    string,
                    PreCECSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setDateExitMill(date: Date): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.dateExitMill = date
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setDateFieldArrival(date: Date): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.dateFieldArrival = date
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setDateExitField(date: Date): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.dateExitField = date
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    fun save(model: PreCECSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_PRE_CEC,
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

}