package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.PreCEC
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.PreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class ICECRepository @Inject constructor(
    private val preCECSharedPreferencesDatasource: PreCECSharedPreferencesDatasource
): CECRepository {
    override suspend fun get(): Result<PreCEC> {
        return runCatching {
            preCECSharedPreferencesDatasource.get()
                .getOrThrow()
                .sharedPreferencesModelToEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setDateExitMill(date: Date): Result<Boolean> {
        val result = preCECSharedPreferencesDatasource.setDateExitMill(date)
        result.onFailure { error ->
            return resultFailure(context = getClassAndMethod(), cause = error)
        }
        return result
    }

    override suspend fun setDateFieldArrival(date: Date): Result<Boolean> {
        val result = preCECSharedPreferencesDatasource.setDateFieldArrival(date)
        result.onFailure { error ->
            return resultFailure(context = getClassAndMethod(), cause = error)
        }
        return result
    }

    override suspend fun setDateExitField(date: Date): Result<Boolean> {
        val result = preCECSharedPreferencesDatasource.setDateExitField(date)
        result.onFailure { error ->
            return resultFailure(context = getClassAndMethod(), cause = error)
        }
        return result
    }
}