package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList

interface CheckListRepository {
    suspend fun saveHeader(entity: HeaderCheckList): Result<Boolean>
    suspend fun cleanResp(): Result<Boolean>
    suspend fun saveResp(entity: RespItemCheckList): Result<Boolean>
    suspend fun saveCheckList(): Result<Boolean>
    suspend fun delLastRespItem(): Result<Boolean>
    suspend fun checkOpen(): Result<Boolean>
}