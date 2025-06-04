package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC
import com.google.gson.Gson
import javax.inject.Inject

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
                context = "INoteMotoMecSharedPreferencesDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(model: NoteMotoMecSharedPreferencesModel): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_NOTE_MOTO_MEC,
                Gson().toJson(model)
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "INoteMotoMecSharedPreferencesDatasource.save",
                message = "-",
                cause = e
            )
        }
    }


    override suspend fun setNroOS(nroOS: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.nroOS = nroOS
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setNroOS",
                    message = e.message,
                    cause = e.cause
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

    override suspend fun setIdActivity(id: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.idActivity = id
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setNroOS",
                    message = e.message,
                    cause = e.cause
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
}