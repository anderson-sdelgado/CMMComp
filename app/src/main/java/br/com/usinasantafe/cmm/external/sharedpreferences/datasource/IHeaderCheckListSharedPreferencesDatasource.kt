package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_HEADER_CHECK_LIST
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject

class IHeaderCheckListSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderCheckListSharedPreferencesDatasource {

    override suspend fun get(): Result<HeaderCheckListSharedPreferencesModel> {
        try {
            val model = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                null
            )
            if(model.isNullOrEmpty())
                return Result.success(
                    HeaderCheckListSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    model,
                    HeaderCheckListSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: HeaderCheckListSharedPreferencesModel): EmptyResult {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                    Gson().toJson(model)
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

    override suspend fun clean(): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                    null
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

    override suspend fun hasOpen(): Result<Boolean> {
        try {
            val model = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_HEADER_CHECK_LIST,
                null
            )
            val check = model.isNullOrEmpty()
            return Result.success(!check)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}