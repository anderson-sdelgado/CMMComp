package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.PreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.PreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_PRE_CEC
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject

class IPreCECSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PreCECSharedPreferencesDatasource {

    override suspend fun get(): Result<PreCECSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val string = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_PRE_CEC,
                null
            )
            if(string.isNullOrEmpty()) return@result PreCECSharedPreferencesModel()
            Gson().fromJson(
                string,
                PreCECSharedPreferencesModel::class.java
            )
        }

    override suspend fun setDateExitMill(date: Date): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.dateExitMill = date
            save(model).getOrThrow()
        }

    override suspend fun setDateFieldArrival(date: Date): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.dateFieldArrival = date
            save(model).getOrThrow()
        }

    override suspend fun setDateExitField(date: Date): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.dateExitField = date
            save(model).getOrThrow()
        }

    suspend fun save(model: PreCECSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_PRE_CEC,
                    Gson().toJson(model)
                )
            }
        }

}