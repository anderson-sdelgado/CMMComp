package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.PreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import java.util.Date
import javax.inject.Inject

class ICECRepository @Inject constructor(
    private val preCECSharedPreferencesDatasource: PreCECSharedPreferencesDatasource
): CECRepository {
    override suspend fun get(): Result<PreCEC> =
        call(getClassAndMethod()) {
            preCECSharedPreferencesDatasource.get()
                .getOrThrow()
                .sharedPreferencesModelToEntity()
        }

    override suspend fun setDateExitMill(date: Date): EmptyResult =
        call(getClassAndMethod()) {
            preCECSharedPreferencesDatasource.setDateExitMill(date).getOrThrow()
        }

    override suspend fun setDateFieldArrival(date: Date): EmptyResult =
        call(getClassAndMethod()) {
            preCECSharedPreferencesDatasource.setDateFieldArrival(date).getOrThrow()
        }

    override suspend fun setDateExitField(date: Date): EmptyResult =
        call(getClassAndMethod()) {
            preCECSharedPreferencesDatasource.setDateExitField(date).getOrThrow()
        }
}