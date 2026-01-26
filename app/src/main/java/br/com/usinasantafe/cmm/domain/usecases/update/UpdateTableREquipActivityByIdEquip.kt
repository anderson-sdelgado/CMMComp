package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.lib.failure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ACTIVITY
import br.com.usinasantafe.cmm.lib.TB_R_EQUIP_ACTIVITY
import br.com.usinasantafe.cmm.presenter.model.emitFailure
import br.com.usinasantafe.cmm.presenter.model.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableREquipActivityByIdEquip {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableREquipActivityByIdEquip @Inject constructor(
    private val getToken: GetToken,
    private val equipRepository: EquipRepository,
    private val rEquipActivityRepository: REquipActivityRepository,
): UpdateTableREquipActivityByIdEquip {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        return@flow runCatching {
            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_R_EQUIP_ACTIVITY)
            val token = getToken().getOrThrow()
            val idEquip = equipRepository.getIdEquipMain().getOrThrow()
            val entityList = rEquipActivityRepository.listByIdEquip(token, idEquip).getOrThrow()
            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_R_EQUIP_ACTIVITY)
            rEquipActivityRepository.deleteAll().getOrThrow()
            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_R_EQUIP_ACTIVITY)
            rEquipActivityRepository.addAll(entityList).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = {
                val failure = failure(getClassAndMethod(), it)
                emitFailure(failure)
            }
        )
    }

}