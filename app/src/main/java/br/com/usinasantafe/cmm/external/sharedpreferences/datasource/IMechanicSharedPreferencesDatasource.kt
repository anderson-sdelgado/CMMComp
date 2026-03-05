package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.MechanicSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.MechanicSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_MECHANIC
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.required
import br.com.usinasantafe.cmm.utils.result
import com.google.gson.Gson
import javax.inject.Inject

class IMechanicSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): MechanicSharedPreferencesDatasource {

    override suspend fun get(): Result<MechanicSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val json = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_MECHANIC,
                null
            )
            if(json.isNullOrEmpty()) return@result MechanicSharedPreferencesModel()
            Gson().fromJson(
                json,
                MechanicSharedPreferencesModel::class.java
            )
        }

    suspend fun save(model: MechanicSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_MECHANIC,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun setNroOS(nroOS: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.nroOS = nroOS
            save(model).getOrThrow()
        }

    override suspend fun setSeqItem(seqItem: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.seqItem = seqItem
            save(model).getOrThrow()
        }

    override suspend fun getNroOS(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow()::nroOS.required()
        }

}