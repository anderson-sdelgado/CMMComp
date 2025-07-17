package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
): CheckListRepository {

    override suspend fun saveHeader(header: HeaderCheckList): Result<Boolean> {
        TODO("Not yet implemented")
    }

}