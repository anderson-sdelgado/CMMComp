package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerPreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_RESP_ITEM_CHECK_LIST_LIST
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_TRAILER_PRE_CEC_LIST
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class ITrailerPreCECSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): TrailerPreCECSharedPreferencesDatasource {

    private val typeToken = object : TypeToken<List<TrailerPreCECSharedPreferencesModel>>() {}.type

    override suspend fun list(): Result<List<TrailerPreCECSharedPreferencesModel>> =
        result(getClassAndMethod()) {
            val result = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_TRAILER_PRE_CEC_LIST,
                null
            )
            if(result.isNullOrEmpty()) return@result emptyList()
            Gson().fromJson(result, typeToken)
        }

    override suspend fun count(): Result<Int> =
        result(getClassAndMethod()) {
            list().getOrThrow().size
        }

    override suspend fun setNroEquipTrailer(nroEquip: Int): EmptyResult =
        result(getClassAndMethod()) {
            val list = list().getOrThrow()
            var mutableList = list.toMutableList()
            if(list.isNotEmpty()) mutableList = list.toMutableList()
            val model = TrailerPreCECSharedPreferencesModel(nroEquip = nroEquip)
            mutableList.add(model)
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_TRAILER_PRE_CEC_LIST,
                    Gson().toJson(mutableList, typeToken)
                )
            }
            mutableList.clear()
        }

    override suspend fun setIdRelease(idRelease: Int): EmptyResult =
        result(getClassAndMethod()) {
            val list = list().getOrThrow()
            val mutableList = list.toMutableList()
            val modelToUpdate = mutableList.find { it.idRelease == null }
            modelToUpdate!!.idRelease = idRelease
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_TRAILER_PRE_CEC_LIST,
                    Gson().toJson(mutableList, typeToken)
                )
            }
            mutableList.clear()
        }

}