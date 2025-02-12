package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.DatasourceException
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import com.google.gson.Gson
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
                return Result.success(ConfigSharedPreferencesModel())
            return Result.success(
                Gson().fromJson(
                    config,
                    ConfigSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "IConfigSharedPreferencesDatasource.getConfig",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasourceImpl.hasConfig",
                    cause = e
                )
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
            return Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasourceImpl.saveConfig",
                    cause = e
                )
            )
        }
    }

}