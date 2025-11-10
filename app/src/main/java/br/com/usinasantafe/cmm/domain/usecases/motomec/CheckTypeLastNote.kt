package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.TypeNote
import javax.inject.Inject

interface CheckTypeLastNote {
    suspend operator fun invoke(): Result<TypeNote>
}

class ICheckTypeLastNote @Inject constructor(
): CheckTypeLastNote {

    override suspend fun invoke(): Result<TypeNote> {
        TODO("Not yet implemented")
    }

}