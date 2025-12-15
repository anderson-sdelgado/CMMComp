package br.com.usinasantafe.cmm.domain.repositories.variable

interface MechanicRepository {
    suspend fun hasNoteOpenByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun setFinishNote(): Result<Boolean>
}