package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_TRAILER_LIST
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class ITrailerSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): TrailerSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<TrailerSharedPreferencesModel>>() {}.type

    override suspend fun add(model: TrailerSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            val list = list().getOrThrow()
            var mutableList = list.toMutableList()
            if(list.isNotEmpty()) mutableList = list.toMutableList()
            mutableList.add(model)
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_TRAILER_LIST,
                    Gson().toJson(mutableList, typeToken)
                )
            }
            mutableList.clear()
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_TRAILER_LIST,
                    null
                )
            }
        }

    override suspend fun has(): Result<Boolean> =
        result(getClassAndMethod()) {
            list().getOrThrow().isNotEmpty()
        }

    override suspend fun list(): Result<List<TrailerSharedPreferencesModel>> =
        result(getClassAndMethod()) {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_TRAILER_LIST,
                null
            )
            if(result.isNullOrEmpty()) return@result emptyList()
            Gson().fromJson(result, typeToken)
        }

}