package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.stringToDouble
import br.com.usinasantafe.cmm.utils.tryCatch
import javax.inject.Inject

interface SetCollection {
    suspend operator fun invoke(
        id: Int,
        value: String,
    ): EmptyResult
}

class ISetCollection @Inject constructor(
    private val fertigationRepository: FertigationRepository,
): SetCollection {

    override suspend fun invoke(
        id: Int,
        value: String,
    ): EmptyResult =
        call(getClassAndMethod()) {
            val valueDouble = tryCatch(::stringToDouble.name) { stringToDouble(value) }
            fertigationRepository.updateCollection(id, valueDouble).getOrThrow()
        }

}