package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuPMMRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.FunctionItemMenu
import br.com.usinasantafe.cmm.utils.TypeActivity
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.TypeView
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListItemMenu {
    suspend operator fun invoke(): Result<List<ItemMenuModel>>
}

class IListItemMenu @Inject constructor(
    private val itemMenuPMMRepository: ItemMenuPMMRepository,
    private val configRepository: ConfigRepository,
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository,
    private val functionActivityRepository: FunctionActivityRepository,
    private val functionStopRepository: FunctionStopRepository
): ListItemMenu {

    override suspend fun invoke(): Result<List<ItemMenuModel>> {
        try {
            val resultGetIdEquipConfig = configRepository.getIdEquip() //ok
            if(resultGetIdEquipConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquipConfig.exceptionOrNull()!!
                )
            }
            val idEquipConfig = resultGetIdEquipConfig.getOrNull()!!
            val resultGetIdEquipMotoMec = motoMecRepository.getIdEquipHeader() //ok
            if(resultGetIdEquipMotoMec.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquipMotoMec.exceptionOrNull()!!
                )
            }
            val idEquipMotoMec = resultGetIdEquipMotoMec.getOrNull()!!

            val list: MutableList<FunctionItemMenu> = mutableListOf()

            // ADD ITEM_NORMAL
            list.add(FunctionItemMenu.ITEM_NORMAL)

            //PERFORMANCE, TRANSHIPMENT, IMPLEMENT, HOSE_COLLECTION
            val resultTypeEquip = equipRepository.getTypeEquipByIdEquip(idEquipMotoMec) //ok
            if(resultTypeEquip.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultTypeEquip.exceptionOrNull()!!
                )
            }
            val typeEquip = resultTypeEquip.getOrNull()!!
            when(typeEquip) {
                TypeEquip.NORMAL -> {
                    val resultGetIdActivity = motoMecRepository.getIdActivityHeader() //ok
                    if(resultGetIdActivity.isFailure){
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultGetIdActivity.exceptionOrNull()!!
                        )
                    }
                    val resultListFunctionActivity = functionActivityRepository.listByIdActivity(resultGetIdActivity.getOrNull()!!) //ok
                    if(resultListFunctionActivity.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultListFunctionActivity.exceptionOrNull()!!
                        )
                    }
                    val functionActivityList = resultListFunctionActivity.getOrNull()!!
                    functionActivityList.forEach {
                        when(it.typeActivity) {
                            TypeActivity.PERFORMANCE -> list.add(FunctionItemMenu.PERFORMANCE)
                            TypeActivity.TRANSHIPMENT -> list.add(FunctionItemMenu.TRANSHIPMENT)
                            TypeActivity.IMPLEMENT -> list.add(FunctionItemMenu.IMPLEMENT)
                            else -> {}
                        }
                    }
                }
                TypeEquip.FERT -> list.add(FunctionItemMenu.HOSE_COLLECTION)
            }

            //MECHANICAL_MAINTENANCE
            val resultGetFlagMechanic = equipRepository.getFlagMechanicByIdEquip(idEquipMotoMec)
            if(resultGetFlagMechanic.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetFlagMechanic.exceptionOrNull()!!
                )
            }
            val flagMechanic = resultGetFlagMechanic.getOrNull()!!
            if(flagMechanic) list.add(FunctionItemMenu.MECHANICAL_MAINTENANCE)

            //TIRE
            val resultGetFlagTire = equipRepository.getFlagTireByIdEquip(idEquipMotoMec)
            if(resultGetFlagTire.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetFlagTire.exceptionOrNull()!!
                )
            }
            val flagTire = resultGetFlagTire.getOrNull()!!
            if(flagTire) list.add(FunctionItemMenu.TIRE)

            //REEL
            val resultGetIdStopReel = functionStopRepository.getIdStopByType(TypeStop.REEL)
            if(resultGetIdStopReel.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdStopReel.exceptionOrNull()!!
                )
            }
            val idStopReel = resultGetIdStopReel.getOrNull()
            if(idStopReel != null) {
                val resultCheckStopReel = motoMecRepository.checkNoteHasByIdStop(idStopReel)
                if(resultCheckStopReel.isFailure){
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultCheckStopReel.exceptionOrNull()!!
                    )
                }
                val checkStopReel = resultCheckStopReel.getOrNull()!!
                if(checkStopReel) list.add(FunctionItemMenu.REEL)
            }

            // ADD BUTTON
            val returnType = if(idEquipConfig == idEquipMotoMec) FunctionItemMenu.FINISH_HEADER else FunctionItemMenu.RETURN_LIST
            list.add(returnType)

            //ADJUST LIST
            val resultList = itemMenuPMMRepository.listByTypeList(list)
            if(resultList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultList.exceptionOrNull()!!
                )
            }
            val listEntity = resultList.getOrNull()!!
            val itemList =  listEntity.map { entity ->
                val type = if(entity.function == FunctionItemMenu.FINISH_HEADER || entity.function == FunctionItemMenu.RETURN_LIST) TypeView.BUTTON else TypeView.ITEM
                ItemMenuModel(
                    id = entity.id,
                    title = entity.title,
                    type = type
                )
            }
            return Result.success(itemList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}