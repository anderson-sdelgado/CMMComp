package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetCollection {
    suspend operator fun invoke(
        id: Int,
        value: String,
    ): EmptyResult
}

class ISetCollection @Inject constructor(
): SetCollection {

    override suspend fun invoke(
        id: Int,
        value: String,
    ): EmptyResult =
        call(getClassAndMethod()) {
            TODO("Not yet implemented")
        }

}