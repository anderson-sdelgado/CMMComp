package br.com.usinasantafe.cmm.infra.datasource.sharedpreferences

import br.com.usinasantafe.cmm.infra.models.sharedpreferences.MechanicSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface MechanicSharedPreferencesDatasource {
    suspend fun get(): Result<MechanicSharedPreferencesModel>
    suspend fun setNroOS(nroOS: Int): EmptyResult
    suspend fun setSeqItem(seqItem: Int): EmptyResult
    suspend fun getNroOS(): Result<Int>
}