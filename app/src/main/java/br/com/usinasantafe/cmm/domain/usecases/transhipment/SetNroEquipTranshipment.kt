package br.com.usinasantafe.cmm.domain.usecases.transhipment

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.motomec.SaveNote
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import com.google.common.primitives.UnsignedInts.toLong
import javax.inject.Inject

interface SetNroEquipTranshipment {
    suspend operator fun invoke(
        nroEquip: String,
        flowApp: FlowApp
    ): EmptyResult
}

class ISetNroEquipTranshipment @Inject constructor(
    private val motoMecRepository: MotoMecRepository,
    private val saveNote: SaveNote
): SetNroEquipTranshipment {
    override suspend fun invoke(
        nroEquip: String,
        flowApp: FlowApp
    ): EmptyResult =
        call(getClassAndMethod()) {
            val nroEquipLong = runCatching {
                nroEquip.toLong()
            }.getOrElse { e ->
                throw Exception(::toLong.name, e)
            }
            motoMecRepository.setNroEquipTranshipmentNote(nroEquipLong).getOrThrow()
            saveNote().getOrThrow()

        }

}