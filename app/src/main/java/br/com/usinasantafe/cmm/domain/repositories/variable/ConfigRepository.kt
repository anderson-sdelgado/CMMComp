package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.StatusSend
import java.util.Date

interface ConfigRepository {
    suspend fun get(): Result<Config>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getPassword(): Result<String>
    suspend fun hasConfig(): Result<Boolean>
    suspend fun send(entity: Config): Result<Config>
    suspend fun save(entity: Config): Result<Boolean>
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean>
    suspend fun getNumber(): Result<Long>
    suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean>
    suspend fun getIdEquip(): Result<Int>
    suspend fun getIdTurnCheckListLast(): Result<Int?>
    suspend fun getDateCheckListLast(): Result<Date>
    suspend fun getNroEquip(): Result<Long>
}