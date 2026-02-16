package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFertigationRepository @Inject constructor(
    private val headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource,
    private val itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource
): FertigationRepository {

    override suspend fun setIdEquipMotorPump(idEquip: Int): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(idEquip).getOrThrow()
        }

    override suspend fun setIdNozzle(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.setIdNozzle(id).getOrThrow()
        }

    override suspend fun getIdNozzle(): Result<Int> {
        TODO("Not yet implemented")
    }

}