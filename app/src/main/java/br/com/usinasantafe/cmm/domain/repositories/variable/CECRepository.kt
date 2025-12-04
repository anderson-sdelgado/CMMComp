package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import java.util.Date

interface CECRepository {
    suspend fun get(): Result<PreCEC>
    suspend fun setDateExitMill(date: Date): Result<Boolean>
    suspend fun setDateFieldArrival(date: Date): Result<Boolean>
    suspend fun setDateExitArrival(date: Date): Result<Boolean>
}