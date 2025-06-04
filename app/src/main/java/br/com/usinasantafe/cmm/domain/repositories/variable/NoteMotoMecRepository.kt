package br.com.usinasantafe.cmm.domain.repositories.variable

interface NoteMotoMecRepository {
    suspend fun setNroOS(nroOS: Int): Result<Boolean>
    suspend fun setIdActivity(id: Int): Result<Boolean>
    suspend fun save(
        idHeader: Int
    ): Result<Boolean>
}