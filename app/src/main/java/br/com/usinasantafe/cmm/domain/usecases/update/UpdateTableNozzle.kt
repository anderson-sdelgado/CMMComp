package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface UpdateTableNozzle {
    suspend operator fun invoke(): Result<Unit>
}

class IUpdateTableNozzle @Inject constructor(
): UpdateTableNozzle {

    override suspend fun invoke(): Result<Unit> =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}