package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.domain.repositories.stable.NozzleRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListNozzle {
    suspend operator fun invoke(): Result<List<Nozzle>>
}

class IListNozzle @Inject constructor(
    private val nozzleRepository: NozzleRepository
): ListNozzle {

    override suspend fun invoke(): Result<List<Nozzle>> =
        call(getClassAndMethod()) {
            nozzleRepository.listAll().getOrThrow()
        }

}