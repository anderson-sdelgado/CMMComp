package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedBytes.toInt
import javax.inject.Inject

interface SetNroOS {
    suspend operator fun invoke(nroOS: String): Result<Unit>
}

class ISetNroOS @Inject constructor(
    private val mechanicRepository: MechanicRepository
): SetNroOS {

    override suspend fun invoke(nroOS: String): Result<Unit> =
        call(getClassAndMethod()) {
            val nroOSInt = runCatching {
                nroOS.toInt()
            }.getOrElse { e ->
                throw Exception(::toInt.name, e)
            }
            mechanicRepository.setNroOS(nroOSInt).getOrThrow()
        }

}