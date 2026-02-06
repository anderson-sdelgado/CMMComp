package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject
import androidx.core.content.edit
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.result

class IHeaderMotoMecSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderMotoMecSharedPreferencesDatasource {

    override suspend fun get(): Result<HeaderMotoMecSharedPreferencesModel> =
        result(getClassAndMethod()) {
            val json = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                null
            )
            if(json.isNullOrEmpty()) HeaderMotoMecSharedPreferencesModel()
            Gson().fromJson(
                json,
                HeaderMotoMecSharedPreferencesModel::class.java
            )
        }

    override suspend fun save(model: HeaderMotoMecSharedPreferencesModel): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                    Gson().toJson(model)
                )
            }
        }

    override suspend fun setRegOperator(regOperator: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.regOperator = regOperator
            save(model).getOrThrow()
        }

    override suspend fun setDataEquip(
        idEquip: Int,
        typeEquip: TypeEquip
    ): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.idEquip = idEquip
            model.typeEquip = typeEquip
            save(model).getOrThrow()
        }

    override suspend fun getTypeEquip(): Result<TypeEquip> =
        result(getClassAndMethod()) {
            get().getOrThrow().typeEquip!!
        }

    override suspend fun setIdTurn(idTurn: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.idTurn = idTurn
            save(model).getOrThrow()
        }

    override suspend fun setNroOS(nroOS: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.nroOS = nroOS
            save(model).getOrThrow()
        }

    override suspend fun setIdActivity(idActivity: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.idActivity = idActivity
            save(model).getOrThrow()
        }

    override suspend fun getNroOS(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow().nroOS!!
        }

    override suspend fun getIdEquip(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow().idEquip!!
        }

    override suspend fun setHourMeter(hourMeter: Double): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.hourMeter = hourMeter
            save(model).getOrThrow()
        }

    override suspend fun clean(): EmptyResult =
        result(getClassAndMethod()) {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_HEADER_MOTO_MEC,
                    null
                )
            }
        }

    override suspend fun setStatusCon(status: Boolean): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.statusCon = status
            save(model).getOrThrow()
        }

    override suspend fun getIdActivity(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow().idActivity!!
        }

    override suspend fun getStatusCon(): Result<Boolean> =
        result(getClassAndMethod()) {
            get().getOrThrow().statusCon!!
        }

    override suspend fun getId(): Result<Int> =
        result(getClassAndMethod()) {
            get().getOrThrow().id!!
        }

    override suspend fun setId(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.id = id
            save(model).getOrThrow()
        }

    override suspend fun setIdEquipMotorPump(idEquip: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = get().getOrThrow()
            model.idEquipMotorPump = idEquip
            save(model).getOrThrow()
        }

}