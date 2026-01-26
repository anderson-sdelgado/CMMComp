package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.ItemRespCheckList
import br.com.usinasantafe.cmm.lib.EmptyResult

interface CheckListRepository {
    suspend fun saveHeader(entity: HeaderCheckList): EmptyResult
    suspend fun cleanResp(): EmptyResult
    suspend fun saveResp(entity: ItemRespCheckList): EmptyResult
    suspend fun saveCheckList(): EmptyResult
    suspend fun delLastRespItem(): EmptyResult
    suspend fun hasOpen(): Result<Boolean>
    suspend fun hasSend(): Result<Boolean>
    suspend fun send(
        number: Long,
        token: String
    ): EmptyResult
}