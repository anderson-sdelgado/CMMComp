package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import javax.inject.Inject

class ICECRepository @Inject constructor(

): CECRepository {
    override suspend fun get(): Result<PreCEC?> {
        TODO("Not yet implemented")
    }
}