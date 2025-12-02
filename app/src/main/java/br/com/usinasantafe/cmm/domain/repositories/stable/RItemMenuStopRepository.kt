package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop

interface RItemMenuStopRepository {
    suspend fun addAll(list: List<RItemMenuStop>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<RItemMenuStop>>
    suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?>
}