package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.typeListPMM
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.presenter.model.functionListPMM
import br.com.usinasantafe.cmm.utils.FERTIGATION
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.MECHANICAL
import br.com.usinasantafe.cmm.utils.PERFORMANCE
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.PMM
import br.com.usinasantafe.cmm.utils.REEL
import br.com.usinasantafe.cmm.utils.TIRE
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.TypeActivity
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListItemMenu {
    suspend operator fun invoke(flavor: String): Result<List<ItemMenuModel>>
}

class IListItemMenu @Inject constructor(
    private val itemMenuRepository: ItemMenuRepository,
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository,
    private val functionActivityRepository: FunctionActivityRepository,
    private val functionStopRepository: FunctionStopRepository
): ListItemMenu {

    override suspend fun invoke(flavor: String): Result<List<ItemMenuModel>> {
        try {

            val resultGetIdEquipMotoMec = motoMecRepository.getIdEquipHeader()
            if(resultGetIdEquipMotoMec.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquipMotoMec.exceptionOrNull()!!
                )
            }
            val idEquipMotoMec = resultGetIdEquipMotoMec.getOrNull()!!

            if(flavor.lowercase() == PMM) {

                val list: MutableList<Pair<Int, String>> = mutableListOf()

                // ADD ITEM_NORMAL
                list.add(typeListPMM.find { it.second == ITEM_NORMAL }!!)

                //PERFORMANCE, TRANSHIPMENT, IMPLEMENT, HOSE_COLLECTION
                val resultTypeEquip = equipRepository.getTypeEquipByIdEquip(idEquipMotoMec)
                if (resultTypeEquip.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultTypeEquip.exceptionOrNull()!!
                    )
                }
                val typeEquip = resultTypeEquip.getOrNull()!!
                when (typeEquip) {
                    TypeEquip.NORMAL -> {
                        val resultGetIdActivity = motoMecRepository.getIdActivityHeader()
                        if (resultGetIdActivity.isFailure) {
                            return resultFailure(
                                context = getClassAndMethod(),
                                cause = resultGetIdActivity.exceptionOrNull()!!
                            )
                        }
                        val resultListFunctionActivity =
                            functionActivityRepository.listByIdActivity(resultGetIdActivity.getOrNull()!!)
                        if (resultListFunctionActivity.isFailure) {
                            return resultFailure(
                                context = getClassAndMethod(),
                                cause = resultListFunctionActivity.exceptionOrNull()!!
                            )
                        }
                        val functionActivityList = resultListFunctionActivity.getOrNull()!!
                        functionActivityList.forEach { function ->
                            when (function.typeActivity) {
                                TypeActivity.PERFORMANCE -> list.add(typeListPMM.find { it.second == PERFORMANCE }!!)
                                TypeActivity.TRANSHIPMENT -> list.add(typeListPMM.find { it.second == TRANSHIPMENT }!!)
                                TypeActivity.IMPLEMENT -> list.add(typeListPMM.find { it.second == IMPLEMENT }!!)
                                else -> {}
                            }
                        }
                    }

                    TypeEquip.FERT -> list.add(typeListPMM.find { it.second == FERTIGATION }!!)
                }

                //MECHANICAL_MAINTENANCE
                val resultGetFlagMechanic = equipRepository.getFlagMechanicByIdEquip(idEquipMotoMec)
                if (resultGetFlagMechanic.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultGetFlagMechanic.exceptionOrNull()!!
                    )
                }
                val flagMechanic = resultGetFlagMechanic.getOrNull()!!
                if (flagMechanic) list.add(typeListPMM.find { it.second == MECHANICAL }!!)

                //TIRE
                val resultGetFlagTire = equipRepository.getFlagTireByIdEquip(idEquipMotoMec)
                if (resultGetFlagTire.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultGetFlagTire.exceptionOrNull()!!
                    )
                }
                val flagTire = resultGetFlagTire.getOrNull()!!
                if (flagTire) list.add(typeListPMM.find { it.second == TIRE }!!)

                //REEL
                val resultGetIdStopReel = functionStopRepository.getIdStopByType(TypeStop.REEL)
                if (resultGetIdStopReel.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultGetIdStopReel.exceptionOrNull()!!
                    )
                }
                val idStopReel = resultGetIdStopReel.getOrNull()
                if (idStopReel != null) {
                    val resultCheckStopReel = motoMecRepository.checkNoteHasByIdStop(idStopReel)
                    if (resultCheckStopReel.isFailure) {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultCheckStopReel.exceptionOrNull()!!
                        )
                    }
                    val checkStopReel = resultCheckStopReel.getOrNull()!!
                    if (checkStopReel) list.add(typeListPMM.find { it.second == REEL }!!)
                }

                //ADJUST LIST
                val resultList = itemMenuRepository.listByTypeList(list)
                if (resultList.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultList.exceptionOrNull()!!
                    )
                }
                val listEntity = resultList.getOrNull()!!
                val itemList = listEntity.map { entity ->
                    val function = functionListPMM.find { it.first == entity.idFunction }!!
                    ItemMenuModel(
                        id = entity.id,
                        descr = entity.descr,
                        function = function
                    )
                }
                return Result.success(itemList)
            }
            return Result.success(emptyList())
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}