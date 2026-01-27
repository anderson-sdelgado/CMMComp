package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemRespCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class IItemRespCheckListSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ItemRespCheckListSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<RespItemCheckListSharedPreferencesModel>>() {}.type

    override suspend fun add(model: RespItemCheckListSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            val list = list().getOrThrow()
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
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST,
                    null
                )
            }
        }

    override suspend fun list(): Result<List<RespItemCheckListSharedPreferencesModel>> =
        result(getClassAndMethod()) {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST,
                null
            )
            if(result.isNullOrEmpty()) return@result emptyList()
            Gson().fromJson(result, typeToken)
        }

    override suspend fun delLast(): EmptyResult =
        result(getClassAndMethod()) {
            val list = list().getOrThrow()
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
        }

}