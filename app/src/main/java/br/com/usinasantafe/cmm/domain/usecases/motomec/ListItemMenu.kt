package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.FERTIGATION
import br.com.usinasantafe.cmm.lib.IMPLEMENT
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.MECHANICAL
import br.com.usinasantafe.cmm.lib.PCOMP
import br.com.usinasantafe.cmm.lib.PCOMP_COMPOUND
import br.com.usinasantafe.cmm.lib.PCOMP_INPUT
import br.com.usinasantafe.cmm.lib.PERFORMANCE
import br.com.usinasantafe.cmm.lib.PMM
import br.com.usinasantafe.cmm.lib.REEL
import br.com.usinasantafe.cmm.lib.TIRE
import br.com.usinasantafe.cmm.lib.TRANSHIPMENT
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.lib.appList
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.lib.typeListPMM
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
            when(flavor.uppercase()){
                PMM -> {
                    val resultList = pmmList()
                    resultList.onFailure {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = it
                        )
                    }
                    val entityList = resultList.getOrNull()!!
                    return Result.success(entityList)
                }
                ECM -> {
                    val app = appList.find { it.second == ECM }!!
                    val resultList = itemMenuRepository.listByApp(app = app)
                    resultList.onFailure {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = it
                        )
                    }
                    val entityList = resultList.getOrNull()!!
                    return Result.success(convertList(entityList = entityList))
                }
                PCOMP -> {
                    val resultComposting = motoMecRepository.getFlowCompostingHeader()
                    resultComposting.onFailure {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = it
                        )
                    }
                    val composting = resultComposting.getOrNull()!!
                    val app = when(composting) {
                        FlowComposting.INPUT -> appList.find { it.second == PCOMP_INPUT }!!
                        FlowComposting.COMPOUND -> appList.find { it.second == PCOMP_COMPOUND }!!
                    }
                    val resultList = itemMenuRepository.listByApp(app = app)
                    resultList.onFailure {
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = it
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

    private suspend fun pmmList(): Result<List<ItemMenuModel>> {
        val resultIdHeader = motoMecRepository.getIdByHeaderOpen()
        resultIdHeader.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val idHeader = resultIdHeader.getOrNull()!!

        val list: MutableList<Pair<Int, String>> = mutableListOf()

        // ADD ITEM_NORMAL
        list.add(typeListPMM.find { it.second == ITEM_NORMAL }!!)

        //PERFORMANCE, TRANSHIPMENT, IMPLEMENT, HOSE_COLLECTION
        val resultTypeEquip = equipRepository.getTypeEquipMain()
        resultTypeEquip.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val typeEquip = resultTypeEquip.getOrNull()!!
        when (typeEquip) {
            TypeEquip.NORMAL -> {
                val resultGetIdActivity = motoMecRepository.getIdActivityHeader()
                resultGetIdActivity.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                val resultListFunctionActivity =
                    functionActivityRepository.listById(resultGetIdActivity.getOrNull()!!) //ok
                resultListFunctionActivity.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
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
            TypeEquip.REEL_FERT -> list.add(typeListPMM.find { it.second == FERTIGATION }!!)
            else -> {}
        }

        //MECHANICAL
        val resultGetFlagMechanic = equipRepository.getFlagMechanic() //ok
        resultGetFlagMechanic.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val flagMechanic = resultGetFlagMechanic.getOrNull()!!
        if (flagMechanic) list.add(typeListPMM.find { it.second == MECHANICAL }!!)

        //TIRE
        val resultGetFlagTire = equipRepository.getFlagTire()
        resultGetFlagTire.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val flagTire = resultGetFlagTire.getOrNull()!!
        if (flagTire) list.add(typeListPMM.find { it.second == TIRE }!!)

        //REEL
        val resultGetIdStopReel = functionStopRepository.getIdStopByType(TypeStop.REEL)
        resultGetIdStopReel.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val idStopReel = resultGetIdStopReel.getOrNull()
        if (idStopReel != null) {
            val resultCheckStopReel = motoMecRepository.hasNoteByIdStopAndIdHeader(idHeader, idStopReel) //ok
            resultCheckStopReel.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val checkStopReel = resultCheckStopReel.getOrNull()!!
            if (checkStopReel) list.add(typeListPMM.find { it.second == REEL }!!)
        }

        //ADJUST LIST
        val resultList = itemMenuRepository.listByTypeList(typeList = list)
        resultList.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
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