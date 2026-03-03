package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemOSMechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ServiceRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.presenter.model.ItemOSMechanicModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface ListItemOS {
    suspend operator fun invoke(): Result<List<ItemOSMechanicModel>>
}

class IListItemOS @Inject constructor(
    private val itemOSMechanicRepository: ItemOSMechanicRepository,
    private val mechanicRepository: MechanicRepository,
    private val equipRepository: EquipRepository,
    private val serviceRepository: ServiceRepository,
    private val componentRepository: ComponentRepository
): ListItemOS {

    override suspend fun invoke(): Result<List<ItemOSMechanicModel>> =
        call(getClassAndMethod()) {
            val idEquip = equipRepository.getIdEquipMain().getOrThrow()
            val nroOS = mechanicRepository.getNroOS().getOrThrow()
            val list = itemOSMechanicRepository.listByIdEquipAndNroOS(idEquip, nroOS).getOrThrow()
            val modelList = list.map { entity ->
                val seq = entity.seqItem
                val serviceEntity = serviceRepository.getById(entity.idServ).getOrThrow()
                val service = serviceEntity?.descr ?: ""
                val componentEntity = componentRepository.getById(entity.idComp).getOrThrow()
                val component = if(componentEntity != null) "${componentEntity.cod} - ${componentEntity.descr}" else ""
                ItemOSMechanicModel(
                    seq = seq,
                    service = service,
                    component = component
                )
            }
            modelList
        }

}