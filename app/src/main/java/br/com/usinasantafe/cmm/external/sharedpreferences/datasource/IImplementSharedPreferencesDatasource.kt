package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ImplementSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ImplementSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_IMPLEMENT_LIST
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Collections.list
import javax.inject.Inject

class IImplementSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ImplementSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<ImplementSharedPreferencesModel>>() {}.type

    override suspend fun add(model: ImplementSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            val list = list().getOrThrow()
            var mutableList = list.toMutableList()
            if(list.isNotEmpty()) mutableList = list.toMutableList()
            mutableList.add(model)
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_IMPLEMENT_LIST,
                    Gson().toJson(mutableList, typeToken)
                )
            }
            mutableList.clear()
        }

    suspend fun has(): Result<Boolean> =
        result(getClassAndMethod()) {
            list().getOrThrow().isNotEmpty()
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_IMPLEMENT_LIST,
                    null
                )
            }
        }

    override suspend fun list(): Result<List<ImplementSharedPreferencesModel>> =
        result(getClassAndMethod()) {
            val result = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_IMPLEMENT_LIST,
                null
            )
            if(result.isNullOrEmpty()) return@result emptyList()
            Gson().fromJson(result, typeToken)
        }

}