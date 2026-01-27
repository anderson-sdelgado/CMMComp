package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemCheckListModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

interface GetItemCheckList {
    suspend operator fun invoke(pos: Int): Result<ItemCheckListModel>
}

class IGetItemCheckList @Inject constructor(
    private val itemCheckListRepository: ItemCheckListRepository,
    private val checkListRepository: CheckListRepository,
    private val equipRepository: EquipRepository,
    private val motoMecRepository: MotoMecRepository,
    private val turnRepository: TurnRepository
): GetItemCheckList {

    override suspend fun invoke(pos: Int): Result<ItemCheckListModel> =
        call(getClassAndMethod()) {
            if(pos == 1) {
                val nroEquip = equipRepository.getNroEquipMain().getOrThrow()
                val regOperator = motoMecRepository.getRegOperatorHeader().getOrThrow()
                val idTurn = motoMecRepository.getIdTurnHeader().getOrThrow()
                val nroTurn = turnRepository.getNroTurnByIdTurn(idTurn).getOrThrow()
                checkListRepository.saveHeader(
                    HeaderCheckList(
                        nroEquip = nroEquip,
                        regOperator = regOperator,
                        nroTurn = nroTurn
                    )
                ).getOrThrow()
            }
            val idCheckList = equipRepository.getIdCheckList().getOrThrow()
            val list = itemCheckListRepository.listByIdCheckList(idCheckList).getOrThrow()
            val entity = list[pos - 1]
            ItemCheckListModel(
                id = entity.idItemCheckList,
                descr = entity.descrItemCheckList
            )
        }

}