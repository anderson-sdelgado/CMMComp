package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject
import androidx.core.content.edit

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
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setRegOperator(regOperator: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.regOperator = regOperator
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: HeaderMotoMecSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                    Gson().toJson(model)
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setDataEquip(
        idEquip: Int,
        typeEquip: TypeEquip
    ): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.idEquip = idEquip
            model.typeEquip = typeEquip
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdTurn(idTurn: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.idTurn = idTurn
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setNroOS(nroOS: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.nroOS = nroOS
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdActivity(idActivity: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.idActivity = idActivity
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getNroOS(): Result<Int> {
        try {
            val result = get()
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val headerMotoMec = result.getOrNull()!!
            return Result.success(headerMotoMec.nroOS!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdEquip(): Result<Int> {
        try {
            val result = get()
            if (result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val headerMotoMec = result.getOrNull()!!
            return Result.success(headerMotoMec.idEquip!!)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setHourMeter(hourMeter: Double): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.hourMeter = hourMeter
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun clean(): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                    null
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setStatusCon(status: Boolean): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.statusCon = status
            val resultSave = save(headerMotoMec)
            if (resultSave.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}