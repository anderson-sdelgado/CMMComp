package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject
import androidx.core.content.edit

class IHeaderMotoMecSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderMotoMecSharedPreferencesDatasource {

    override suspend fun get(): Result<HeaderMotoMecSharedPreferencesModel> {
        try {
            val model = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                null
            )
            if(model.isNullOrEmpty())
                return Result.success(
                    HeaderMotoMecSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    model,
                    HeaderMotoMecSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setRegOperator(regOperator: Int): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.regOperator = regOperator
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
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setDataEquip(
        idEquip: Int,
        typeEquipMain: TypeEquipMain
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
            model.idEquip = idEquip
            model.typeEquipMain = typeEquipMain
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

    override suspend fun setIdTurn(idTurn: Int): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.idTurn = idTurn
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

    override suspend fun setNroOS(nroOS: Int): Result<Boolean> {
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

    override suspend fun setIdActivity(idActivity: Int): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.idActivity = idActivity
            val resultSave = save(headerMotoMec)
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

    override suspend fun getNroOS(): Result<Int> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val headerMotoMec = result.getOrNull()!!
            return Result.success(headerMotoMec.nroOS!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdEquip(): Result<Int> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.idEquip!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setHourMeter(hourMeter: Double): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.hourMeter = hourMeter
            val resultSave = save(headerMotoMec)
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
                    BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
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

    override suspend fun setStatusCon(status: Boolean): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val headerMotoMec = resultGet.getOrNull()!!
            headerMotoMec.statusCon = status
            val resultSave = save(headerMotoMec)
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

    override suspend fun getStatusCon(): Result<Boolean> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.statusCon!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getId(): Result<Int> {
        try {
            val result = get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = result.getOrNull()!!
            return Result.success(model.id!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setId(id: Int): Result<Boolean> {
        try {
            val resultGet = get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val model = resultGet.getOrNull()!!
            model.id = id
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