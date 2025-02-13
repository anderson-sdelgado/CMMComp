package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipPneu
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipPneuRepository
import javax.inject.Inject

class IREquipPneuRepository @Inject constructor() : REquipPneuRepository {

    override suspend fun addAll(list: List<REquipPneu>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<REquipPneu>> {
        TODO("Not yet implemented")
    }
}