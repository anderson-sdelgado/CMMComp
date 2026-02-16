package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import br.com.usinasantafe.cmm.utils.EmptyResult
import java.util.Date

interface CECRepository {
    suspend fun get(): Result<PreCEC>
    suspend fun setDateExitMill(date: Date): EmptyResult
    suspend fun setDateFieldArrival(date: Date): EmptyResult
    suspend fun setDateExitField(date: Date): EmptyResult
    suspend fun hasCouplingTrailer(): Result<Boolean>
    suspend fun uncouplingTrailer(): EmptyResult
}