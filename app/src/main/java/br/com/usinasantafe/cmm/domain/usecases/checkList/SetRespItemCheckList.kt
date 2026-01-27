package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.entities.variable.ItemRespCheckList
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

interface SetRespItemCheckList {
    suspend operator fun invoke(
        pos: Int,
        id: Int,
        option: OptionRespCheckList
    ): Result<Boolean>
}

class ISetRespItemCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val equipRepository: EquipRepository,
    private val itemCheckListRepository: ItemCheckListRepository,
    private val startWorkManager: StartWorkManager
): SetRespItemCheckList {

    override suspend fun invoke(
        pos: Int,
        id: Int,
        option: OptionRespCheckList
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            if(pos == 1) checkListRepository.cleanResp().getOrThrow()
            val entity = ItemRespCheckList(
                idItem = id,
                option = option
            )
            checkListRepository.saveResp(entity).getOrThrow()
            val idCheckList = equipRepository.getIdCheckList().getOrThrow()
            val count = itemCheckListRepository.countByIdCheckList(idCheckList).getOrThrow()
            val check =  pos < count
            if(!check) {
                checkListRepository.saveCheckList().getOrThrow()
                startWorkManager()
            }
            check
        }

}