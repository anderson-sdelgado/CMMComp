package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import android.util.Log
import br.com.usinasantafe.cmm.domain.errors.AppError
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.StatusSend
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject

class IConfigSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ConfigSharedPreferencesDatasource {

    override suspend fun get(): Result<ConfigSharedPreferencesModel> {
        try {
            val config = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            if(config.isNullOrEmpty())
                return Result.success(
                    ConfigSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    config,
                    ConfigSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun has(): Result<Boolean> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            return Result.success(result != null)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.has",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                Gson().toJson(model)
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val resultConfig = get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.setFlagUpdate",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = resultConfig.getOrNull()!!
            config.flagUpdate = flagUpdate
            val resultSave = save(config)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.setFlagUpdate",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.setFlagUpdate",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getPassword(): Result<String> {
        try {
            val result = get()
            if(result.isFailure){
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.getPassword",
                    message = e.message,
                    cause = e.cause
                    )
            }
            val config = result.getOrNull()!!
            return Result.success(config.password!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.getPassword",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        try {
            val result = get()
            if(result.isFailure){
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.getFlagUpdate",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.flagUpdate)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.getFlagUpdate",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getNumber(): Result<Long> {
        try {
            val result = get()
            if(result.isFailure){
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.getNumber",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.number!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.getNumber",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean> {
        try {
            val resultConfig = get()
            if (resultConfig.isFailure) {
                val e = resultConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.setStatusSend",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = resultConfig.getOrNull()!!
            config.statusSend = statusSend
            val resultSave = save(config)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.setStatusSend",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.setStatusSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdEquip(): Result<Int> {
        try {
            val result = get()
            if(result.isFailure){
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.getIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.idEquip!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.getIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdTurnCheckListLast(): Result<Int?> {
        try {
            val result = get()
            if(result.isFailure){
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.getIdTurnCheckListLast",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.idTurnCheckListLast)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.getIdTurnCheckListLast",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getDateCheckListLast(): Result<Date> {
        try {
            val result = get()
            if(result.isFailure){
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IConfigSharedPreferencesDatasource.getDateCheckListLast",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.dateLastCheckList!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IConfigSharedPreferencesDatasource.getDateCheckListLast",
                message = "-",
                cause = e
            )
        }
    }

}