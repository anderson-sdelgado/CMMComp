package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ITEM_CHECK_LIST
import br.com.usinasantafe.cmm.presenter.model.emitProgress
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.flowCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableItemCheckListByNroEquip {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableItemCheckListByNroEquip @Inject constructor(
    private val getToken: GetToken,
    private val itemCheckListRepository: ItemCheckListRepository,
    private val equipRepository: EquipRepository,
): UpdateTableItemCheckListByNroEquip {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_ITEM_CHECK_LIST)
            val token = getToken().getOrThrow()
            val nroEquip = equipRepository.getNroEquipMain().getOrThrow()
            val entityList = itemCheckListRepository.listByNroEquip(token, nroEquip).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_ITEM_CHECK_LIST)
            itemCheckListRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_ITEM_CHECK_LIST)
            itemCheckListRepository.addAll(entityList).getOrThrow()

        }
    }

}