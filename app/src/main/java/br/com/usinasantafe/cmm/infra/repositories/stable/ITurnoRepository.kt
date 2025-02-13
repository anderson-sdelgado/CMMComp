package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turno
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnoRepository
import javax.inject.Inject

class ITurnoRepository @Inject constructor() : TurnoRepository {

    override suspend fun addAll(list: List<Turno>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Turno>> {
        TODO("Not yet implemented")
    }
}