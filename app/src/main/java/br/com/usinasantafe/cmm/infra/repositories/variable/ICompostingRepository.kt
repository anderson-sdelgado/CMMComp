package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.repositories.variable.CompostingRepository
import javax.inject.Inject

class ICompostingRepository @Inject constructor(

): CompostingRepository {
    override suspend fun hasCompostingInputLoadSent(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}