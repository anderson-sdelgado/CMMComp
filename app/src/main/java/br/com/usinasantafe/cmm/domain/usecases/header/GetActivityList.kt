package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import javax.inject.Inject

interface GetActivityList {
    suspend operator fun invoke(): Result<List<Activity>>
}

class IGetActivityList @Inject constructor(
    private val configRepository: ConfigRepository,
    private val headerMotoMecRepository: HeaderMotoMecRepository,
    private val rEquipActivityRepository: REquipActivityRepository,
    private val osRepository: OSRepository,
    private val rOSActivityRepository: ROSActivityRepository,
    private val activityRepository: ActivityRepository,
): GetActivityList {

    override suspend fun invoke(): Result<List<Activity>> {
        try {
            val resultGetConfig = configRepository.get()
            if (resultGetConfig.isFailure) {
                val e = resultGetConfig.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetActivityList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultGetREquipActivityList = rEquipActivityRepository.getListByIdEquip(config.idEquip!!)
            if(resultGetREquipActivityList.isFailure) {
                val e = resultGetREquipActivityList.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetActivityList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val rEquipActivityList = resultGetREquipActivityList.getOrNull()!!
            var idActivityEquipList = rEquipActivityList.map { it.idActivity }
            val resultGetNroOS = headerMotoMecRepository.getNroOS()
            if (resultGetNroOS.isFailure) {
                val e = resultGetNroOS.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetActivityList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val nroOS = resultGetNroOS.getOrNull()!!
            val resultGetListByNroOS = osRepository.listByNroOS(nroOS)
            if (resultGetListByNroOS.isFailure) {
                val e = resultGetListByNroOS.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetActivityList",
                    message = e.message,
                    cause = e.cause
                )
            }
            val list = resultGetListByNroOS.getOrNull()!!
            if (list.isNotEmpty()) {
                val os = list.first()
                val resultGetListByNroOSActivity = rOSActivityRepository.listByIdOS(
                    idOS = os.idOS
                )
                if (resultGetListByNroOSActivity.isFailure) {
                    val e = resultGetListByNroOSActivity.exceptionOrNull()!!
                    return resultFailure(
                        context = "IGetActivityList",
                        message = e.message,
                        cause = e.cause
                    )
                }
                val rOSActivityList = resultGetListByNroOSActivity.getOrNull()!!
                val idActivityOSList = rOSActivityList.map { it.idActivity }
                idActivityEquipList = idActivityEquipList.intersect(idActivityOSList.toSet()).toList()
            }
            val resultGetActivityListByIdEquip = activityRepository.listByIdList(idActivityEquipList)
            if (resultGetActivityListByIdEquip.isFailure) {
                val e = resultGetActivityListByIdEquip.exceptionOrNull()!!
                return resultFailure(
                    context = "IGetActivityList",
                    message = e.message,
                    cause = e.cause
                )
            }
            return resultGetActivityListByIdEquip
        } catch (e: Exception) {
            return resultFailure(
                context = "IGetActivityList",
                message = "-",
                cause = e
            )
        }
    }

}