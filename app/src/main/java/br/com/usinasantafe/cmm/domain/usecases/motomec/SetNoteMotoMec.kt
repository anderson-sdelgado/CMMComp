package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNoteMotoMec {
    suspend operator fun invoke(item: ItemMenuModel): Result<Boolean>
}

class ISetNoteMotoMec @Inject constructor(
    private val rItemMenuStopRepository: RItemMenuStopRepository,
    private val motoMecRepository: MotoMecRepository,
): SetNoteMotoMec {

    override suspend fun invoke(item: ItemMenuModel): Result<Boolean> {
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

            // The last expression is the success value for runCatching
            motoMecRepository.saveNote(idHeader).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}