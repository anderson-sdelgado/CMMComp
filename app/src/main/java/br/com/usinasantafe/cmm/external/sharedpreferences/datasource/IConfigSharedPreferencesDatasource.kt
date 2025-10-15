package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject
import androidx.core.content.edit

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
                context = getClassAndMethod(),
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
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_CONFIG,
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

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val resultConfig = get()
            if (resultConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultConfig.exceptionOrNull()!!
                )
            }
            val config = resultConfig.getOrNull()!!
            config.flagUpdate = flagUpdate
            val resultSave = save(config)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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

    override suspend fun getPassword(): Result<String> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.password!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.flagUpdate)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getNumber(): Result<Long> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.number!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean> {
        try {
            val resultConfig = get()
            if (resultConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultConfig.exceptionOrNull()!!
                )
            }
            val config = resultConfig.getOrNull()!!
            config.statusSend = statusSend
            val resultSave = save(config)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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

    override suspend fun getIdEquip(): Result<Int> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.idEquip!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdTurnCheckListLast(): Result<Int?> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.idTurnCheckListLast)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getDateCheckListLast(): Result<Date> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.dateLastCheckList!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getNroEquip(): Result<Long> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.nroEquip!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}