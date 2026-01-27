package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.call
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

    override suspend fun invoke(): Result<List<Activity>> =
        call(getClassAndMethod()) {
            val idEquip = equipRepository.getIdEquipMain().getOrThrow()
            val rEquipActivityList = rEquipActivityRepository.listByIdEquip(idEquip).getOrThrow()
            var idActivityEquipList = rEquipActivityList.map { it.idActivity }
            val nroOS = motoMecRepository.getNroOSHeader().getOrThrow()
            val list = osRepository.listByNroOS(nroOS).getOrThrow()
            if (list.isNotEmpty()) {
                val os = list.first()
                val rOSActivityList = rOSActivityRepository.listByIdOS(
                    idOS = os.idOS
                ).getOrThrow()
                val idActivityOSList = rOSActivityList.map { it.idActivity }
                idActivityEquipList = idActivityEquipList.intersect(idActivityOSList.toSet()).toList()
            }
            activityRepository.listByIdList(idActivityEquipList).getOrThrow()
        }

}