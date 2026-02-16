package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetValuePressure {
    suspend operator fun invoke(value: Double): Result<Unit>
}

class ISetValuePressure @Inject constructor(
): SetValuePressure {

    override suspend fun invoke(value: Double): Result<Unit> =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}