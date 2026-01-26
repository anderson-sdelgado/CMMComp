package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import java.util.Date

interface ConfigRepository {
    suspend fun get(): Result<Config>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getPassword(): Result<String>
    suspend fun hasConfig(): Result<Boolean>
    suspend fun send(entity: Config): Result<Config>
    suspend fun save(entity: Config): EmptyResult
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult
    suspend fun getNumber(): Result<Long>
    suspend fun setStatusSend(statusSend: StatusSend): EmptyResult
    suspend fun getIdTurnCheckListLast(): Result<Int?>
    suspend fun getDateCheckListLast(): Result<Date>
}