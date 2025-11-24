package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemCheckListModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface GetItemCheckList {
    suspend operator fun invoke(pos: Int): Result<ItemCheckListModel>
}

class IGetItemCheckList @Inject constructor(
    private val itemCheckListRepository: ItemCheckListRepository,
    private val checkListRepository: CheckListRepository,
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository,
    private val motoMecRepository: MotoMecRepository,
    private val turnRepository: TurnRepository
): GetItemCheckList {

    override suspend fun invoke(pos: Int): Result<ItemCheckListModel> {
        try {
            if(pos == 1) {
                val resultGetNroEquip = configRepository.getNroEquip()
                resultGetNroEquip.onFailure { return Result.failure(it) }
                val nroEquip = resultGetNroEquip.getOrNull()!!
                val resultGetRegOperator = motoMecRepository.getRegOperatorHeader()
                resultGetRegOperator.onFailure { return Result.failure(it) }
                val regOperator = resultGetRegOperator.getOrNull()!!
                val resultGetIdTurn = motoMecRepository.getIdTurnHeader()
                resultGetIdTurn.onFailure { return Result.failure(it) }
                val idTurn = resultGetIdTurn.getOrNull()!!
                val resultGetNroTurn = turnRepository.getNroTurnByIdTurn(idTurn)
                resultGetNroTurn.onFailure { return Result.failure(it) }
                val nroTurn = resultGetNroTurn.getOrNull()!!
                val resultSaveHeader = checkListRepository.saveHeader(
                    HeaderCheckList(
                        nroEquip = nroEquip,
                        regOperator = regOperator,
                        nroTurn = nroTurn
                    )
                )
                resultSaveHeader.onFailure { return Result.failure(it) }
            }
            val resultGetIdEquip = configRepository.getIdEquip()
            resultGetIdEquip.onFailure { return Result.failure(it) }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetIdCheckList = equipRepository.getIdCheckListByIdEquip(idEquip)
            resultGetIdCheckList.onFailure { return Result.failure(it) }
            val idCheckList = resultGetIdCheckList.getOrNull()!!
            val resultListCheckList = itemCheckListRepository.listByIdCheckList(idCheckList)
            resultListCheckList.onFailure { return Result.failure(it) }
            val list = resultListCheckList.getOrNull()!!
            val entity = list[pos - 1]
            val model = ItemCheckListModel(
                id = entity.idItemCheckList,
                descr = entity.descrItemCheckList
            )
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}