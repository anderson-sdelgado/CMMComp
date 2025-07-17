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
                if (resultGetNroEquip.isFailure) {
                    val e = resultGetNroEquip.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetItemCheckList",
                        message = e.message,
                        cause = e.cause
                    )
                }
                val nroEquip = resultGetNroEquip.getOrNull()!!
                val resultGetRegOperator = motoMecRepository.getRegOperator()
                if (resultGetRegOperator.isFailure) {
                    val e = resultGetRegOperator.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetItemCheckList",
                        message = e.message,
                        cause = e.cause
                    )
                }
                val regOperator = resultGetRegOperator.getOrNull()!!
                val resultGetIdTurn = motoMecRepository.getIdTurnHeader()
                if (resultGetIdTurn.isFailure) {
                    val e = resultGetIdTurn.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetItemCheckList",
                        message = e.message,
                        cause = e.cause
                    )
                }
                val idTurn = resultGetIdTurn.getOrNull()!!
                val resultGetNroTurn = turnRepository.geNroTurnByIdTurn(idTurn)
                if (resultGetNroTurn.isFailure) {
                    val e = resultGetNroTurn.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetItemCheckList",
                        message = e.message,
                        cause = e.cause
                    )
                }
                val nroTurn = resultGetNroTurn.getOrNull()!!
                val resultSaveHeader = checkListRepository.saveHeader(
                    header = HeaderCheckList(
                        nroEquip = nroEquip,
                        regOperator = regOperator,
                        nroTurn = nroTurn
                    )
                )
                if (resultSaveHeader.isFailure) {
                    val e = resultSaveHeader.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetItemCheckList",
                        message = e.message,
                        cause = e.cause
                    )
                }
            }
            val resultGetIdEquip = configRepository.getIdEquip()
            if (resultGetIdEquip.isFailure) {
                val e = resultGetIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetItemCheckList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetIdCheckList = equipRepository.getIdCheckListByIdEquip(idEquip)
            if (resultGetIdCheckList.isFailure) {
                val e = resultGetIdCheckList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetItemCheckList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val idCheckList = resultGetIdCheckList.getOrNull()!!
            val resultListCheckList = itemCheckListRepository.listByIdCheckList(idCheckList)
            if (resultListCheckList.isFailure) {
                val e = resultListCheckList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetItemCheckList",
                    message = e.message,
                    cause = e.cause
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
            return resultFailure(
                context = "IGetItemCheckList",
                message = "-",
                cause = e
            )
        }
    }

}