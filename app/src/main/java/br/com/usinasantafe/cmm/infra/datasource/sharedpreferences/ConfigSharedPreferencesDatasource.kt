package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import java.util.Date

interface ConfigSharedPreferencesDatasource {
    suspend fun get(): Result<ConfigSharedPreferencesModel>
    suspend fun has(): Result<Boolean>
    suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean>
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean>
    suspend fun getPassword(): Result<String>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getNumber(): Result<Long>
    suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean>
    suspend fun getIdEquip(): Result<Int>
    suspend fun getIdTurnCheckListLast(): Result<Int?>
    suspend fun getDateCheckListLast(): Result<Date>
    suspend fun getNroEquip(): Result<Long>

}