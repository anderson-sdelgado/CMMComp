package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasNoteMotoMec {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasNoteMotoMec @Inject constructor(
    private val motoMecRepository: MotoMecRepository
): HasNoteMotoMec {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetId = motoMecRepository.getIdByHeaderOpen()
            if (resultGetId.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetId.exceptionOrNull()!!
                )
            }
            val id = resultGetId.getOrNull()!!
            val resultCheck = motoMecRepository.hasNoteByIdHeader(id)
            if (resultCheck.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheck.exceptionOrNull()!!
                )
            }
            return resultCheck
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}