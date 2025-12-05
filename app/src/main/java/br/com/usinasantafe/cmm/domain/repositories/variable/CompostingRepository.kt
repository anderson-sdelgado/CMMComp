package br.com.usinasantafe.cmm.domain.repositories.variable

interface CompostingRepository {
    suspend fun hasCompostingInputLoadSent(): Result<Boolean>
}