package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderPreCEC
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import java.util.Date
import javax.inject.Inject

class ICECRepository @Inject constructor(
    private val headerPreCECSharedPreferencesDatasource: HeaderPreCECSharedPreferencesDatasource,
    private val trailerSharedPreferencesDatasource: TrailerSharedPreferencesDatasource,
    private val trailerPreCECSharedPreferencesDatasource: TrailerPreCECSharedPreferencesDatasource
): CECRepository {

    override suspend fun get(): Result<HeaderPreCEC> =
        call(getClassAndMethod()) {
            headerPreCECSharedPreferencesDatasource.get()
                .getOrThrow()
                .sharedPreferencesModelToEntity()
        }

    override suspend fun setDateExitMillHeaderPreCEC(date: Date): EmptyResult =
        call(getClassAndMethod()) {
            headerPreCECSharedPreferencesDatasource.setDateExitMill(date).getOrThrow()
        }

    override suspend fun setDateFieldArrivalHeaderPreCEC(date: Date): EmptyResult =
        call(getClassAndMethod()) {
            headerPreCECSharedPreferencesDatasource.setDateFieldArrival(date).getOrThrow()
        }

    override suspend fun setDateExitFieldHeaderPreCEC(date: Date): EmptyResult =
        call(getClassAndMethod()) {
            headerPreCECSharedPreferencesDatasource.setDateExitField(date).getOrThrow()
        }

    override suspend fun hasCouplingTrailer(): Result<Boolean> =
        call(getClassAndMethod()) {
            trailerSharedPreferencesDatasource.has().getOrThrow()
        }

    override suspend fun uncouplingTrailer(): EmptyResult =
        call(getClassAndMethod()) {
            trailerSharedPreferencesDatasource.clean().getOrThrow()
        }

    override suspend fun setDataHeaderPreCEC(
        nroEquip: Long,
        regColab: Long,
        nroTurn: Int,
    ): EmptyResult =
        call(getClassAndMethod()) {
            headerPreCECSharedPreferencesDatasource.setData(nroEquip, regColab, nroTurn).getOrThrow()
        }

    override suspend fun setIdReleasePreCEC(idRelease: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            val count = trailerPreCECSharedPreferencesDatasource.count().getOrThrow()
            if(count == 0) {
                headerPreCECSharedPreferencesDatasource.setIdRelease(idRelease).getOrThrow()
            } else {
                trailerPreCECSharedPreferencesDatasource.setIdRelease(idRelease).getOrThrow()
            }
            (count == 4)
        }

}