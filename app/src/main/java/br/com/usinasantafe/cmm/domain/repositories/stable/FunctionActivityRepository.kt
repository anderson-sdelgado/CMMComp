package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeActivity

interface FunctionActivityRepository {
    suspend fun addAll(list: List<FunctionActivity>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<FunctionActivity>>
    suspend fun listById(idActivity: Int): Result<List<FunctionActivity>>
    suspend fun hasByIdAndType(idActivity: Int, typeActivity: TypeActivity): Result<Boolean>
}