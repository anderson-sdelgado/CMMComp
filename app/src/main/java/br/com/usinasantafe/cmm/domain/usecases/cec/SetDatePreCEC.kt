package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.lib.EXIT_MILL
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.lib.StatusPreCEC
import br.com.usinasantafe.cmm.lib.FIELD_ARRIVAL
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

interface SetDatePreCEC {
    suspend operator fun invoke(item: ItemMenuModel): Result<StatusPreCEC>
}

class ISetDatePreCEC @Inject constructor(
    private val cecRepository: CECRepository
): SetDatePreCEC {

    override suspend fun invoke(item: ItemMenuModel): Result<StatusPreCEC> {

        val model = cecRepository.get().getOrElse {
            return resultFailure(context = getClassAndMethod(), cause = it)
        }

        val currentStatus = when {
            model.dateMillExit == null -> StatusPreCEC.EXIT_MILL
            model.dateFieldArrival == null -> StatusPreCEC.FIELD_ARRIVAL
            else -> StatusPreCEC.EXIT_ARRIVAL
        }

        val expectedStatus = when (item.type.second) {
            EXIT_MILL -> StatusPreCEC.EXIT_MILL
            FIELD_ARRIVAL -> StatusPreCEC.FIELD_ARRIVAL
            else -> StatusPreCEC.EXIT_ARRIVAL
        }

        if (currentStatus != expectedStatus) {
            return Result.success(currentStatus)
        }

        val updateResult = when(item.type.second) {
            EXIT_MILL -> cecRepository.setDateExitMill(Date())
            FIELD_ARRIVAL -> cecRepository.setDateFieldArrival(Date())
            else -> cecRepository.setDateExitArrival(Date())
        }

        updateResult.onFailure {
            return resultFailure(context = getClassAndMethod(), cause = it)
        }

        return Result.success(currentStatus)
    }

}
