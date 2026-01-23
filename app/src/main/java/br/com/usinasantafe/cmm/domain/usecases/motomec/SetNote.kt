package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNote {
    suspend operator fun invoke(item: ItemMenuModel): EmptyResult
}

class ISetNote @Inject constructor(
    private val rItemMenuStopRepository: RItemMenuStopRepository,
    private val motoMecRepository: MotoMecRepository,
    private val functionActivityRepository: FunctionActivityRepository,
): SetNote {

    override suspend fun invoke(item: ItemMenuModel): EmptyResult {
        return runCatching {
            val idStop = rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                idFunction = item.function.first,
                idApp = item.app.first
            ).getOrThrow()

            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
            val idActivity = motoMecRepository.getIdActivityHeader().getOrThrow()

            motoMecRepository.setNroOSNote(nroOS).getOrThrow()
            motoMecRepository.setIdActivityNote(idActivity).getOrThrow()

            if (idStop != null) {
                motoMecRepository.setIdStop(idStop).getOrThrow()
            }

            motoMecRepository.saveNote(idHeader).getOrThrow()

            val checkPerformance = functionActivityRepository.hasByIdAndType(
                idActivity = idActivity,
                typeActivity = TypeActivity.PERFORMANCE
            ).getOrThrow()

            if(checkPerformance) {
                motoMecRepository.insertInitialPerformance().getOrThrow()
            }

        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}