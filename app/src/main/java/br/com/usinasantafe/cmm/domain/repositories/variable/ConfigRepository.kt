package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.utils.FlagUpdate

interface ConfigRepository {
    suspend fun getConfig(): Result<Config>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getPassword(): Result<String>
    suspend fun hasConfig(): Result<Boolean>
    suspend fun send(config: Config): Result<Int>
    suspend fun save(config: Config): Result<Boolean>
}