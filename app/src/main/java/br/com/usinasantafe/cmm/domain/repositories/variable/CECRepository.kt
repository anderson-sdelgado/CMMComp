package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderPreCEC
import br.com.usinasantafe.cmm.utils.EmptyResult
import java.util.Date

interface CECRepository {
    suspend fun get(): Result<HeaderPreCEC>
    suspend fun setDateExitMillHeaderPreCEC(date: Date): EmptyResult
    suspend fun setDateFieldArrivalHeaderPreCEC(date: Date): EmptyResult
    suspend fun setDateExitFieldHeaderPreCEC(date: Date): EmptyResult
    suspend fun hasCouplingTrailer(): Result<Boolean>
    suspend fun uncouplingTrailer(): EmptyResult
    suspend fun setDataHeaderPreCEC(
        nroEquip: Long,
        regColab: Long,
        nroTurn: Int
    ): EmptyResult
    suspend fun setIdReleasePreCEC(idRelease: Int): Result<Boolean>
}