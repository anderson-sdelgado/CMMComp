package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TB_ITEM_OS_MECHANIC
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.emitProgress
import br.com.usinasantafe.cmm.utils.flowCall
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableItemOSMechanicByIdEquipAndNroOS {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState>
}

class IUpdateTableItemOSMechanicByIdEquipAndNroOS @Inject constructor(
    private val getToken: GetToken,
    private val equipRepository: EquipRepository,
    private val mechanicRepository: MechanicRepository,
    private val itemOSMechanicRepository: ItemOSMechanicRepository
): UpdateTableItemOSMechanicByIdEquipAndNroOS {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<UpdateStatusState> = flow {
        flowCall(getClassAndMethod()) {

            emitProgress(count, sizeAll, LevelUpdate.RECOVERY, TB_ITEM_OS_MECHANIC)
            val token = getToken().getOrThrow()
            val idEquip = equipRepository.getIdEquipMain().getOrThrow()
            val nroOS = mechanicRepository.getNroOS().getOrThrow()
            val list = itemOSMechanicRepository.listByIdEquipAndNroOS(token, idEquip, nroOS).getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.CLEAN, TB_ITEM_OS_MECHANIC)
            itemOSMechanicRepository.deleteAll().getOrThrow()

            emitProgress(count, sizeAll, LevelUpdate.SAVE, TB_ITEM_OS_MECHANIC)
            itemOSMechanicRepository.addAll(list).getOrThrow()
        }
    }
}