package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Colab
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface ColabRepository : UpdateRepository<Colab> {
    suspend fun hasByReg(reg: Int): Result<Boolean>
}