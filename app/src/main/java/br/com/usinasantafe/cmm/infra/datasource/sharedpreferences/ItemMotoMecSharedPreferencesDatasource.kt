package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel

interface ItemMotoMecSharedPreferencesDatasource {
    suspend fun get(): Result<NoteMotoMecSharedPreferencesModel>
    suspend fun save(model: NoteMotoMecSharedPreferencesModel): Result<Boolean>
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