package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.utils.EmptyResult

interface MechanicRepository {
    suspend fun hasNoteOpenByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun setFinishNote(): EmptyResult
    suspend fun setNroOS(nroOS: Int): EmptyResult
    suspend fun setSeqItem(seqItem: Int): EmptyResult
    suspend fun getNroOS(): Result<Int>
    suspend fun save(idHeader: Int): EmptyResult
}