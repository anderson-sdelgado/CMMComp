package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemMotoMecSharedPreferencesDatasource {
    suspend fun get(): Result<ItemMotoMecSharedPreferencesModel>
    suspend fun save(model: ItemMotoMecSharedPreferencesModel): EmptyResult
    suspend fun setNroOSAndStatusCon(
        nroOS: Int,
        statusCon: Boolean
    ): EmptyResult
    suspend fun setIdActivity(id: Int): EmptyResult
    suspend fun getIdActivity(): Result<Int>
    suspend fun setIdStop(id: Int): EmptyResult
    suspend fun clean(): EmptyResult
    suspend fun setNroEquipTranshipment(nroEquip: Long): EmptyResult
    suspend fun setIdNozzle(id: Int): EmptyResult
}