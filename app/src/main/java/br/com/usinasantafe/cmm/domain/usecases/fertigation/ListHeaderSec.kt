package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.presenter.model.ItemDefaultScreenModel
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.required
import javax.inject.Inject

interface ListHeaderSec {
    suspend operator fun invoke(): Result<List<ItemDefaultScreenModel>>
}

class IListHeaderSec @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val equipRepository: EquipRepository
): ListHeaderSec {

    override suspend fun invoke(): Result<List<ItemDefaultScreenModel>> =
        call(getClassAndMethod()) {
            val idEquipMain = equipRepository.getIdEquipMain().getOrThrow()
            val idHeaderMain = motoMecRepository.getIdHeaderByIdEquipAndOpen(idEquipMain).getOrThrow()
            val listHeaderSec = motoMecRepository.listHeaderSecByIdHeader(idHeaderMain).getOrThrow()
            listHeaderSec.map {
                with(it){
                    val nroEquip = equipRepository.getNroById(::idEquip.required()).getOrThrow()
                    ItemDefaultScreenModel(
                        id = ::id.required(),
                        descr = "$nroEquip"
                    )
                }
            }
        }

}