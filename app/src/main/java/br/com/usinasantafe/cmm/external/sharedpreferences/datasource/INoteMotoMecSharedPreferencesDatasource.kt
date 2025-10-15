package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject
import androidx.core.content.edit

class INoteMotoMecSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): NoteMotoMecSharedPreferencesDatasource {

    override suspend fun get(): Result<NoteMotoMecSharedPreferencesModel> {
        try {
            val noteMotoMec = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC,
                null
            )
            if(noteMotoMec.isNullOrEmpty())
                return Result.success(
                    NoteMotoMecSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    noteMotoMec,
                    NoteMotoMecSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: NoteMotoMecSharedPreferencesModel): Result<Boolean> {
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
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.nroOS = nroOS
            model.statusCon = statusCon
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.idActivity = id
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "INoteMotoMecSharedPreferencesDatasource.setNroOS",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdActivity(): Result<Int> {
        try {
            val result = get()
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.idActivity!!)
        } catch (e: Exception){
            return resultFailure(
                context = "INoteMotoMecSharedPreferencesDatasource.getIdActivity",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdStop(id: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.idStop = id
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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
}