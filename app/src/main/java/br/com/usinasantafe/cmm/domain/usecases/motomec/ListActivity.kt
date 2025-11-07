package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListActivity {
    suspend operator fun invoke(): Result<List<Activity>>
}

class IListActivity @Inject constructor(
    private val configRepository: ConfigRepository,
    private val motoMecRepository: MotoMecRepository,
    private val rEquipActivityRepository: REquipActivityRepository,
    private val osRepository: OSRepository,
    private val rOSActivityRepository: ROSActivityRepository,
    private val activityRepository: ActivityRepository,
): ListActivity {

    override suspend fun invoke(): Result<List<Activity>> {
        try {
            val resultGetConfig = configRepository.get()
            if (resultGetConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetConfig.exceptionOrNull()!!
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultGetREquipActivityList = rEquipActivityRepository.listByIdEquip(config.idEquip!!)
            if(resultGetREquipActivityList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetREquipActivityList.exceptionOrNull()!!
                )
            }
            val rEquipActivityList = resultGetREquipActivityList.getOrNull()!!
            var idActivityEquipList = rEquipActivityList.map { it.idActivity }
            val resultGetNroOS = motoMecRepository.getNroOSHeader()
            if (resultGetNroOS.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetNroOS.exceptionOrNull()!!
                )
            }
            val nroOS = resultGetNroOS.getOrNull()!!
            val resultGetListByNroOS = osRepository.listByNroOS(nroOS)
            if (resultGetListByNroOS.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetListByNroOS.exceptionOrNull()!!
                )
            }
            val list = resultGetListByNroOS.getOrNull()!!
            if (list.isNotEmpty()) {
                val os = list.first()
                val resultGetListByNroOSActivity = rOSActivityRepository.listByIdOS(
                    idOS = os.idOS
                )
                if (resultGetListByNroOSActivity.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultGetListByNroOSActivity.exceptionOrNull()!!
                    )
                }
                val rOSActivityList = resultGetListByNroOSActivity.getOrNull()!!
                val idActivityOSList = rOSActivityList.map { it.idActivity }
                idActivityEquipList = idActivityEquipList.intersect(idActivityOSList.toSet()).toList()
            }
            val resultGetActivityListByIdEquip = activityRepository.listByIdList(idActivityEquipList)
            if (resultGetActivityListByIdEquip.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetActivityListByIdEquip.exceptionOrNull()!!
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