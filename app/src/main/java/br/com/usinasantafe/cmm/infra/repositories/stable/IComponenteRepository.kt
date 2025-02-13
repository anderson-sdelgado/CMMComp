package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Componente
import br.com.usinasantafe.cmm.domain.repositories.stable.ComponenteRepository
import javax.inject.Inject

class IComponenteRepository @Inject constructor() : ComponenteRepository {

    override suspend fun addAll(list: List<Componente>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Componente>> {
        TODO("Not yet implemented")
    }
}