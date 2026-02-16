package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.stringToDouble
import br.com.usinasantafe.cmm.utils.tryCatch
import javax.inject.Inject

interface CheckPerformance {
    suspend operator fun invoke(
        id: Int,
        value: String,
    ): Result<Boolean>
}

class ICheckPerformance @Inject constructor(
    private val osRepository: OSRepository,
    private val performanceRepository: PerformanceRepository,
): CheckPerformance {

    override suspend fun invoke(
        id: Int,
        value: String,
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            val valueInput = tryCatch(::stringToDouble.name) { stringToDouble(value) }
            val nroOS = performanceRepository.getNroOSById(id).getOrThrow()
            val check = osRepository.hasByNroOS(nroOS).getOrThrow()
            if(!check) return@call valueInput <= 150.0
            val valueBD = osRepository.getByNroOS(nroOS).getOrThrow().areaOS
            return@call valueInput <= valueBD
        }

}