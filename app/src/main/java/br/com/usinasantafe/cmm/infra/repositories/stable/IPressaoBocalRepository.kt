package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.PressaoBocal
import br.com.usinasantafe.cmm.domain.repositories.stable.PressaoBocalRepository
import javax.inject.Inject

class IPressaoBocalRepository @Inject constructor() : PressaoBocalRepository {

    override suspend fun addAll(list: List<PressaoBocal>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<PressaoBocal>> {
        TODO("Not yet implemented")
    }
}