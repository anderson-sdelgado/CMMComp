package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC
import com.google.gson.Gson
import javax.inject.Inject

class IHeaderMotoMecSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderMotoMecSharedPreferencesDatasource {

    override suspend fun get(): Result<HeaderMotoMecSharedPreferencesModel> {
        try {
            val headerMotoMec = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                null
            )
            if(headerMotoMec.isNullOrEmpty())
                return Result.success(
                    HeaderMotoMecSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    headerMotoMec,
                    HeaderMotoMecSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.get",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setRegOperator(regOperator: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setRegOperator",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.regOperator = regOperator
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setRegOperator",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.setRegOperator",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun save(model: HeaderMotoMecSharedPreferencesModel): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                Gson().toJson(model)
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdEquip(idEquip: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.idEquip = idEquip
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.setIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdTurn(idTurn: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdTurn",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.idTurn = idTurn
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdTurn",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.setIdTurn",
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
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setNroOS",
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
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.setNroOS",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setIdActivity(idActivity: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdActivity",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.idActivity = idActivity
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setIdActivity",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.setIdActivity",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getNroOS(): Result<Int> {
        try {
            val result = get()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.getNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = result.getOrNull()!!
            return Result.success(headerMotoMec.nroOS!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.getNroOS",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdEquip(): Result<Int> {
        try {
            val result = get()
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.getIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = result.getOrNull()!!
            return Result.success(headerMotoMec.idEquip!!)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.getIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setMeasureInitial(measure: Double): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.measureInitial = measure
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                val e = resultSave.exceptionOrNull()!!
                return resultFailure(
                    context = "IHeaderMotoMecSharedPreferencesDatasource.setMeasureInitial",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IHeaderMotoMecSharedPreferencesDatasource.setIdActivity",
                message = "-",
                cause = e
            )
        }
    }

}