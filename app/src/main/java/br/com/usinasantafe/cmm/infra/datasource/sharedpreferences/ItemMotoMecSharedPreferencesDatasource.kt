package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel

interface ItemMotoMecSharedPreferencesDatasource {
    suspend fun get(): Result<ItemMotoMecSharedPreferencesModel>
    suspend fun save(model: ItemMotoMecSharedPreferencesModel): Result<Boolean>
    suspend fun setNroOSAndStatusCon(
        nroOS: Int,
        statusCon: Boolean
    ): Result<Boolean>
    suspend fun setIdActivity(id: Int): Result<Boolean>
    suspend fun getIdActivity(): Result<Int>
    suspend fun setIdStop(id: Int): Result<Boolean>
    suspend fun clean(): Result<Boolean>
    suspend fun setNroEquipTranshipment(nroEquipTranshipment: Long): Result<Boolean>
}