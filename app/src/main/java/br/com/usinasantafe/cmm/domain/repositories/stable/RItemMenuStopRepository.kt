package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop
import br.com.usinasantafe.cmm.utils.EmptyResult

interface RItemMenuStopRepository {
    suspend fun addAll(list: List<RItemMenuStop>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<RItemMenuStop>>
    suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?>
}