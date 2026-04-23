package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderPreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_HEADER_PRE_CEC
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject

class IHeaderPreCECSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderPreCECSharedPreferencesDatasource {

    suspend fun save(model: HeaderPreCECSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_HEADER_PRE_CEC,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun get(): Result<HeaderPreCECSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val string = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_HEADER_PRE_CEC,
                null
            )
            if(string.isNullOrEmpty()) return@result HeaderPreCECSharedPreferencesModel()
            Gson().fromJson(
                string,
                HeaderPreCECSharedPreferencesModel::class.java
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

    override suspend fun setData(
        nroEquip: Long,
        regColab: Long,
        nroTurn: Int
    ): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.nroEquip = nroEquip
            model.regColab = regColab
            model.nroTurn = nroTurn
            save(model).getOrThrow()
        }

    override suspend fun setIdRelease(idRelease: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.idRelease = idRelease
            save(model).getOrThrow()
        }

}