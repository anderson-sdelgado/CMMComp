package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList

interface CheckListRepository {
    suspend fun saveHeader(header: HeaderCheckList): Result<Boolean>
}