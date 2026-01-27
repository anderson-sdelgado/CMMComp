package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject
import androidx.core.content.edit
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.result

class IConfigSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ConfigSharedPreferencesDatasource {

    override suspend fun get(): Result<ConfigSharedPreferencesModel?> =
        result(getClassAndMethod()) {
            val config = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            if(config.isNullOrEmpty()) return@result null
            Gson().fromJson(
                config,
                ConfigSharedPreferencesModel::class.java
            )
        }

    override suspend fun has(): Result<Boolean> =
        result(getClassAndMethod()) {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            result != null
        }

    override suspend fun save(model: ConfigSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult =
        result(getClassAndMethod()) {
            val resultConfig = get()
            resultConfig.onFailure {
                throw Exception(it)
            }
            val model = resultConfig.getOrNull()!!
            model.flagUpdate = flagUpdate
            val resultSave = save(model)
            resultSave.onFailure {
                throw Exception(it)
            }
        }

    override suspend fun getPassword(): Result<String> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.password
        }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.flagUpdate
        }

    override suspend fun getNumber(): Result<Long> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.number
        }

    override suspend fun setStatusSend(statusSend: StatusSend): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model!!.statusSend = statusSend
            save(model).getOrThrow()
        }

    override suspend fun getIdTurnCheckListLast(): Result<Int?> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.idTurnCheckListLast
        }

    override suspend fun getDateCheckListLast(): Result<Date> =
        result(getClassAndMethod()) {
            get().getOrThrow()!!.dateLastCheckList!!
        }

}