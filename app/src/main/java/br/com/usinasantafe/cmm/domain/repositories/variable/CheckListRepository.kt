package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList

interface CheckListRepository {
    suspend fun saveHeader(entity: HeaderCheckList): Result<Boolean>
    suspend fun clearResp(): Result<Boolean>
    suspend fun saveResp(entity: RespItemCheckList): Result<Boolean>
    suspend fun saveCheckList(): Result<Boolean>

}