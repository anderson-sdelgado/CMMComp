package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.stringToDouble
import br.com.usinasantafe.cmm.utils.tryCatch
import javax.inject.Inject

interface SetPerformance {
    suspend operator fun invoke(
        id: Int,
        value: String,
    ): EmptyResult
}

class ISetPerformance @Inject constructor(
    private val performanceRepository: PerformanceRepository,
): SetPerformance {

    override suspend fun invoke(
        id: Int,
        value: String,
    ): EmptyResult =
        call(getClassAndMethod()) {
            val valueDouble = tryCatch(::stringToDouble.name) { stringToDouble(value) }
            performanceRepository.update(id, valueDouble).getOrThrow()
        }


}