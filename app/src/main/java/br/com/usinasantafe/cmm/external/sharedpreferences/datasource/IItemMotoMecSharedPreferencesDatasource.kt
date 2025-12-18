package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject
import androidx.core.content.edit

class IItemMotoMecSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ItemMotoMecSharedPreferencesDatasource {

    override suspend fun get(): Result<ItemMotoMecSharedPreferencesModel> {
        try {
            val noteMotoMec = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC,
                null
            )
            if(noteMotoMec.isNullOrEmpty())
                return Result.success(
                    ItemMotoMecSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    noteMotoMec,
                    ItemMotoMecSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: ItemMotoMecSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC,
                    Gson().toJson(model)
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


    override suspend fun setNroOSAndStatusCon(
        nroOS: Int,
        statusCon: Boolean
    ): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.nroOS = nroOS
            model.statusCon = statusCon
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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

    override suspend fun setIdActivity(id: Int): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.idActivity = id
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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

    override suspend fun getIdActivity(): Result<Int> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.idActivity!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdStop(id: Int): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.idStop = id
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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

    override suspend fun clean(): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC,
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

    override suspend fun setNroEquipTranshipment(nroEquipTranshipment: Long): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.nroEquipTranshipment = nroEquipTranshipment
            val resultSave = save(model)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
}