package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_TRAILER_LIST
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class ITrailerSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): TrailerSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<TrailerSharedPreferencesModel>>() {}.type

    override suspend fun add(model: TrailerSharedPreferencesModel): Result<Boolean> {
        try {
            val resultList = list()
            resultList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val list = resultList.getOrNull()!!
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
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun clean(): EmptyResult {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_TRAILER_LIST,
                    null
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

    override suspend fun has(): Result<Boolean> {
        try {
            val resultList = list()
            resultList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val list = resultList.getOrNull()!!
            return Result.success(list.isNotEmpty())
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun list(): Result<List<TrailerSharedPreferencesModel>> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_TRAILER_LIST,
                null
            )
            if(result.isNullOrEmpty()) return Result.success(emptyList())
            return Result.success(
                Gson().fromJson(result, typeToken)
            )
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}