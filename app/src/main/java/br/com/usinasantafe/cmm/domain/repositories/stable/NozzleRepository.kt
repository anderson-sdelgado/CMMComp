package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.utils.EmptyResult

interface NozzleRepository {
    suspend fun addAll(list: List<Nozzle>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<Nozzle>>
    suspend fun listAll(): Result<List<Nozzle>>
}