package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListSpeed {
    suspend operator fun invoke(): Result<Unit>
}

class IListSpeed @Inject constructor(
): ListSpeed {

    override suspend fun invoke(): Result<Unit> =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}