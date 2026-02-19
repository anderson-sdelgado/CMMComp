package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.RItemMenuStopRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetNote {
    suspend operator fun invoke(item: ItemMenuModel): EmptyResult
}

class ISetNote @Inject constructor(
    private val rItemMenuStopRepository: RItemMenuStopRepository,
    private val motoMecRepository: MotoMecRepository,
    private val saveNote: SaveNote
): SetNote {

    override suspend fun invoke(item: ItemMenuModel): EmptyResult =
        call(getClassAndMethod()) {
            val idStop = rItemMenuStopRepository.getIdStopByIdFunctionAndIdApp(
                idFunction = item.function.first,
                idApp = item.app.first
            ).getOrThrow()

            if (idStop != null) {
                motoMecRepository.setIdStop(idStop).getOrThrow()
            }

            saveNote().getOrThrow()

        }

}