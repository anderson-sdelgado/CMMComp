package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerPreCECSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface TrailerPreCECSharedPreferencesDatasource {
    suspend fun list(): Result<List<TrailerPreCECSharedPreferencesModel>>
    suspend fun count(): Result<Int>
    suspend fun setNroEquipTrailer(nroEquip: Int): EmptyResult
    suspend fun setIdRelease(idRelease: Int): EmptyResult
}