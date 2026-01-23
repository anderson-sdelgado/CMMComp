package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CompostingRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface HasCompostingInputLoadSentOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class IHasCompostingInputLoadSentOpen @Inject constructor(
    private val compostingRepository: CompostingRepository
): HasCompostingInputLoadSentOpen {

    override suspend fun invoke(): Result<Boolean> {
        val result = compostingRepository.hasCompostingInputLoadSent()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

}