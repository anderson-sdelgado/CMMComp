package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.lib.EmptyResult

interface MechanicRepository {
    suspend fun hasNoteOpenByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun setFinishNote(): EmptyResult
}