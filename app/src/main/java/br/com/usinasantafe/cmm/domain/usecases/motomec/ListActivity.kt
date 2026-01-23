package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListActivity {
    suspend operator fun invoke(): Result<List<Activity>>
}

class IListActivity @Inject constructor(
    private val equipRepository: EquipRepository,
    private val motoMecRepository: MotoMecRepository,
    private val rEquipActivityRepository: REquipActivityRepository,
    private val osRepository: OSRepository,
    private val rOSActivityRepository: ROSActivityRepository,
    private val activityRepository: ActivityRepository,
): ListActivity {

    override suspend fun invoke(): Result<List<Activity>> {
        try {
            val resultGetIdEquip = equipRepository.getIdEquipMain()
            resultGetIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetREquipActivityList = rEquipActivityRepository.listByIdEquip(idEquip)
            resultGetREquipActivityList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val rEquipActivityList = resultGetREquipActivityList.getOrNull()!!
            var idActivityEquipList = rEquipActivityList.map { it.idActivity }
            val resultGetNroOS = motoMecRepository.getNroOSHeader()
            resultGetNroOS.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val nroOS = resultGetNroOS.getOrNull()!!
            val resultGetListByNroOS = osRepository.listByNroOS(nroOS)
            resultGetListByNroOS.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val list = resultGetListByNroOS.getOrNull()!!
            if (list.isNotEmpty()) {
                val os = list.first()
                val resultGetListByNroOSActivity = rOSActivityRepository.listByIdOS(
                    idOS = os.idOS
                )
                resultGetListByNroOSActivity.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                val rOSActivityList = resultGetListByNroOSActivity.getOrNull()!!
                val idActivityOSList = rOSActivityList.map { it.idActivity }
                idActivityEquipList = idActivityEquipList.intersect(idActivityOSList.toSet()).toList()
            }
            val resultGetActivityListByIdEquip = activityRepository.listByIdList(idActivityEquipList)
            resultGetActivityListByIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return resultGetActivityListByIdEquip
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}