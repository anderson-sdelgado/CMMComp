package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.lib.resultFailure
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
        return runCatching {
            when(flavor.uppercase()){
                PMM -> {
                    pmmList().getOrThrow()
                }
                ECM -> {
                    val app = appList.find { it.second == ECM }!!
                    val entityList = itemMenuRepository.listByApp(app = app).getOrThrow()
                    convertList(entityList = entityList)
                }
                PCOMP -> {
                    val composting = motoMecRepository.getFlowCompostingHeader().getOrThrow()
                    val app = when(composting) {
                        FlowComposting.INPUT -> appList.find { it.second == PCOMP_INPUT }!!
                        FlowComposting.COMPOUND -> appList.find { it.second == PCOMP_COMPOUND }!!
                    }
                    val entityList = itemMenuRepository.listByApp(app = app).getOrThrow()
                    convertList(entityList = entityList)
                }
                else -> {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = Exception("Flavor not found")
                    )
                }
            }

        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    private suspend fun pmmList(): Result<List<ItemMenuModel>> {
        return runCatching {

            val idHeader = motoMecRepository.getIdByHeaderOpen().getOrThrow()
            val list: MutableList<Pair<Int, String>> = mutableListOf()

            // ADD ITEM_NORMAL
            list.add(typeListPMM.find { it.second == ITEM_NORMAL }!!)

            //PERFORMANCE, TRANSHIPMENT, IMPLEMENT, HOSE_COLLECTION
            val typeEquip = equipRepository.getTypeEquipMain().getOrThrow()
            when (typeEquip) {
                TypeEquip.NORMAL -> {
                    val idActivity = motoMecRepository.getIdActivityHeader().getOrThrow()
                    val functionActivityList = functionActivityRepository.listById(idActivity).getOrThrow()
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
            val flagMechanic = equipRepository.getFlagMechanic().getOrThrow()
            if (flagMechanic) list.add(typeListPMM.find { it.second == MECHANICAL }!!)

            //TIRE
            val flagTire = equipRepository.getFlagTire().getOrThrow()
            if (flagTire) list.add(typeListPMM.find { it.second == TIRE }!!)

            //REEL
            val idStopReel = functionStopRepository.getIdStopByType(TypeStop.REEL).getOrThrow()
            if (idStopReel != null) {
                val checkStopReel = motoMecRepository.hasNoteByIdStopAndIdHeader(idHeader, idStopReel).getOrThrow()
                if (checkStopReel) list.add(typeListPMM.find { it.second == REEL }!!)
            }

            //ADJUST LIST
            val entityList = itemMenuRepository.listByTypeList(typeList = list).getOrThrow()
            convertList(entityList = entityList)

        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
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