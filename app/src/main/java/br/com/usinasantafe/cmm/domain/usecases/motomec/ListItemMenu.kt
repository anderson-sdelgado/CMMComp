package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.FlowComposting
import br.com.usinasantafe.cmm.utils.ECM
import br.com.usinasantafe.cmm.utils.FERTIGATION
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.MECHANICAL
import br.com.usinasantafe.cmm.utils.PCOMP
import br.com.usinasantafe.cmm.utils.PCOMP_COMPOUND
import br.com.usinasantafe.cmm.utils.PCOMP_INPUT
import br.com.usinasantafe.cmm.utils.PERFORMANCE
import br.com.usinasantafe.cmm.utils.PMM
import br.com.usinasantafe.cmm.utils.REEL
import br.com.usinasantafe.cmm.utils.TIRE
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.TypeActivity
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.appList
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.typeListPMM
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
            resultGetIdEquipMotoMec.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdEquipMotoMec.exceptionOrNull()!!
                )
            }
            val idEquipMotoMec = resultGetIdEquipMotoMec.getOrNull()!!

            when(flavor.uppercase()){
                PMM -> {
                    return pmmList(idEquipMotoMec)
                }
                ECM -> {
                    val app = appList.find { it.second == ECM }!!
                    val resultList = itemMenuRepository.listByTypeList(app = app)
                    resultList.onFailure {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultList.exceptionOrNull()!!
                        )
                    }
                    val entityList = resultList.getOrNull()!!
                    return Result.success(convertList(entityList = entityList))
                }
                PCOMP -> {
                    val resultComposting = motoMecRepository.getCompostingHeader()
                    resultComposting.onFailure {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultComposting.exceptionOrNull()!!
                        )
                    }
                    val composting = resultComposting.getOrNull()!!
                    val app = when(composting) {
                        FlowComposting.INPUT -> appList.find { it.second == PCOMP_INPUT }!!
                        FlowComposting.COMPOUND -> appList.find { it.second == PCOMP_COMPOUND }!!
                    }
                    val resultList = itemMenuRepository.listByTypeList(app = app)
                    resultList.onFailure {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultList.exceptionOrNull()!!
                        )
                    }
                    val entityList = resultList.getOrNull()!!
                    return Result.success(convertList(entityList = entityList))
                }
                else -> {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = Exception("Flavor not found")
                    )
                }
            }

        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    private suspend fun pmmList(idEquip: Int): Result<List<ItemMenuModel>> {
        val resultIdHeader = motoMecRepository.getIdByHeaderOpen()
        resultIdHeader.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultIdHeader.exceptionOrNull()!!
            )
        }
        val idHeader = resultIdHeader.getOrNull()!!

        val list: MutableList<Pair<Int, String>> = mutableListOf()

        // ADD ITEM_NORMAL
        list.add(typeListPMM.find { it.second == ITEM_NORMAL }!!)

        //PERFORMANCE, TRANSHIPMENT, IMPLEMENT, HOSE_COLLECTION
        val resultTypeEquip = equipRepository.getTypeEquipByIdEquip(idEquip)
        resultTypeEquip.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultTypeEquip.exceptionOrNull()!!
            )
        }
        val typeEquip = resultTypeEquip.getOrNull()!!
        when (typeEquip) {
            TypeEquip.NORMAL -> {
                val resultGetIdActivity = motoMecRepository.getIdActivityHeader()
                resultGetIdActivity.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultGetIdActivity.exceptionOrNull()!!
                    )
                }
                val resultListFunctionActivity =
                    functionActivityRepository.listByIdActivity(resultGetIdActivity.getOrNull()!!)
                resultListFunctionActivity.onFailure {
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

        //MECHANICAL
        val resultGetFlagMechanic = equipRepository.getFlagMechanicByIdEquip(idEquip)
        resultGetFlagMechanic.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultGetFlagMechanic.exceptionOrNull()!!
            )
        }
        val flagMechanic = resultGetFlagMechanic.getOrNull()!!
        if (flagMechanic) list.add(typeListPMM.find { it.second == MECHANICAL }!!)

        //TIRE
        val resultGetFlagTire = equipRepository.getFlagTireByIdEquip(idEquip)
        resultGetFlagTire.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultGetFlagTire.exceptionOrNull()!!
            )
        }
        val flagTire = resultGetFlagTire.getOrNull()!!
        if (flagTire) list.add(typeListPMM.find { it.second == TIRE }!!)

        //REEL
        val resultGetIdStopReel = functionStopRepository.getIdStopByType(TypeStop.REEL)
        resultGetIdStopReel.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultGetIdStopReel.exceptionOrNull()!!
            )
        }
        val idStopReel = resultGetIdStopReel.getOrNull()
        if (idStopReel != null) {
            val resultCheckStopReel = motoMecRepository.hasNoteByIdStopAndIdHeader(idHeader, idStopReel)
            resultCheckStopReel.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckStopReel.exceptionOrNull()!!
                )
            }
            val checkStopReel = resultCheckStopReel.getOrNull()!!
            if (checkStopReel) list.add(typeListPMM.find { it.second == REEL }!!)
        }

        //ADJUST LIST
        val app = appList.find { it.second == PMM }!!
        val resultList = itemMenuRepository.listByTypeList(typeList = list, app = app)
        resultList.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultList.exceptionOrNull()!!
            )
        }
        val entityList = resultList.getOrNull()!!
        return Result.success(convertList(entityList = entityList))
    }

    private fun convertList(entityList: List<ItemMenu>): List<ItemMenuModel> {
        return entityList.map { entity ->
            ItemMenuModel(
                id = entity.id,
                descr = entity.descr,
                function = entity.function,
                type = entity.type,
                app = entity.app
            )
        }
    }
}