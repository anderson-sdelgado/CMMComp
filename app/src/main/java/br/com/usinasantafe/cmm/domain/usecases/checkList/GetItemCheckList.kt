package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemCheckListModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.util.Date
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
                val resultGetNroEquip = configRepository.getNroEquip() // ok
                if (resultGetNroEquip.isFailure) {
                    return resultFailureMiddle(
                        context = getClassAndMethod(),
                        cause = resultGetNroEquip.exceptionOrNull()!!
                    )
                }
                val nroEquip = resultGetNroEquip.getOrNull()!!
                val resultGetRegOperator = motoMecRepository.getRegOperator()
                if (resultGetRegOperator.isFailure) {
                    return resultFailureMiddle(
                        context = getClassAndMethod(),
                        cause = resultGetRegOperator.exceptionOrNull()!!
                    )
                }
                val regOperator = resultGetRegOperator.getOrNull()!!
                val resultGetIdTurn = motoMecRepository.getIdTurnHeader()
                if (resultGetIdTurn.isFailure) {
                    return resultFailureMiddle(
                        context = getClassAndMethod(),
                        cause = resultGetIdTurn.exceptionOrNull()!!
                    )
                }
                val idTurn = resultGetIdTurn.getOrNull()!!
                val resultGetNroTurn = turnRepository.geNroTurnByIdTurn(idTurn)
                if (resultGetNroTurn.isFailure) {
                    return resultFailureMiddle(
                        context = getClassAndMethod(),
                        cause = resultGetNroTurn.exceptionOrNull()!!
                    )
                }
                val nroTurn = resultGetNroTurn.getOrNull()!!
                val resultSaveHeader = checkListRepository.saveHeader(
                    HeaderCheckList(
                        nroEquip = nroEquip,
                        regOperator = regOperator,
                        nroTurn = nroTurn
                    )
                )
                if (resultSaveHeader.isFailure) {
                    return resultFailureMiddle(
                        context = getClassAndMethod(),
                        cause = resultSaveHeader.exceptionOrNull()!!
                    )
                }
            }
            val resultGetIdEquip = configRepository.getIdEquip()
            if (resultGetIdEquip.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquip.exceptionOrNull()!!
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetIdCheckList = equipRepository.getIdCheckListByIdEquip(idEquip)
            if (resultGetIdCheckList.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetIdCheckList.exceptionOrNull()!!
                )
            }
            val idCheckList = resultGetIdCheckList.getOrNull()!!
            val resultListCheckList = itemCheckListRepository.listByIdCheckList(idCheckList)
            if (resultListCheckList.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultListCheckList.exceptionOrNull()!!
                )
            }
            val list = resultListCheckList.getOrNull()!!
            val entity = list[pos - 1]
            val model = ItemCheckListModel(
                id = entity.idItemCheckList,
                descr = entity.descrItemCheckList
            )
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}