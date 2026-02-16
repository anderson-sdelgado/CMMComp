package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Colab
import br.com.usinasantafe.cmm.utils.EmptyResult


interface ColabRepository {
    suspend fun addAll(list: List<Colab>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<Colab>>
    suspend fun hasByReg(reg: Int): Result<Boolean>
}