package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_HEADER_CHECK_LIST
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import javax.inject.Inject

class IHeaderCheckListSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderCheckListSharedPreferencesDatasource {

    override suspend fun get(): Result<HeaderCheckListSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val model = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                null
            )
            if(model.isNullOrEmpty()) return@result HeaderCheckListSharedPreferencesModel()
            Gson().fromJson(
                model,
                HeaderCheckListSharedPreferencesModel::class.java
            )
        }

    override suspend fun save(model: HeaderCheckListSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                    null
                )
            }
        }

    override suspend fun hasOpen(): Result<Boolean> =
        result(getClassAndMethod()) {
            val model = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                null
            )
            !model.isNullOrEmpty()
        }

}