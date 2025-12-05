package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemRespCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class IItemRespCheckListSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ItemRespCheckListSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<RespItemCheckListSharedPreferencesModel>>() {}.type

    override suspend fun add(model: RespItemCheckListSharedPreferencesModel): Result<Boolean> {
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
                    BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST,
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

    override suspend fun clean(): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST,
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

    override suspend fun list(): Result<List<RespItemCheckListSharedPreferencesModel>> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST,
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

    override suspend fun delLast(): Result<Boolean> {
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
            val count = mutableList.count()
            mutableList.removeAt(count - 1)
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST,
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

}