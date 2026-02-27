package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNroOS {
    suspend operator fun invoke(nroOS: String): Result<Unit>
}

class ISetNroOS @Inject constructor(
): SetNroOS {

    override suspend fun invoke(nroOS: String): Result<Unit> =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}