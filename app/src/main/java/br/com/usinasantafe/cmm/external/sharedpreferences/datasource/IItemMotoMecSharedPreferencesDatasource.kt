package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARED_PREFERENCES_TABLE_NOTE_MOTO_MEC
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject
import androidx.core.content.edit
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.result

class IItemMotoMecSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ItemMotoMecSharedPreferencesDatasource {

    override suspend fun get(): Result<ItemMotoMecSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val noteMotoMec = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_NOTE_MOTO_MEC,
                null
            )
            if(noteMotoMec.isNullOrEmpty()) return@result ItemMotoMecSharedPreferencesModel()
            Gson().fromJson(
                noteMotoMec,
                ItemMotoMecSharedPreferencesModel::class.java
            )
        }

    override suspend fun save(model: ItemMotoMecSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_NOTE_MOTO_MEC,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun setIdStop(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.idStop = id
            save(model).getOrThrow()
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_NOTE_MOTO_MEC,
                    null
                )
            }
        }

    override suspend fun setNroEquipTranshipment(nroEquip: Long): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.nroEquipTranshipment = nroEquip
            save(model).getOrThrow()
        }

    override suspend fun setIdNozzle(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.idNozzle = id
            save(model).getOrThrow()
        }

    override suspend fun getIdNozzle(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow().idNozzle!!
        }

    override suspend fun setValuePressure(value: Double): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.valuePressure = value
            save(model).getOrThrow()
        }

    override suspend fun getValuePressure(): Result<Double> =
        result(getClassAndMethod()) {
            get().getOrThrow().valuePressure!!
        }

    override suspend fun setSpeedPressure(speed: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.speedPressure = speed
            save(model).getOrThrow()
        }

}