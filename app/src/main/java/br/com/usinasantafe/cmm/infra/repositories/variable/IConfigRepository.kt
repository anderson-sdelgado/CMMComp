package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.retrofitToEntity
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class IConfigRepository @Inject constructor(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource,
): ConfigRepository {

    override suspend fun get(): Result<Config> {
        return runCatching {
            val config = configSharedPreferencesDatasource.get().getOrThrow() ?: return Result.success(Config())
            config.sharedPreferencesModelToEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        return runCatching {
            configSharedPreferencesDatasource.getFlagUpdate().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getPassword(): Result<String> {
        return runCatching {
            configSharedPreferencesDatasource.getPassword().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasConfig(): Result<Boolean> {
        return runCatching {
            configSharedPreferencesDatasource.has().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun send(entity: Config): Result<Config> {
        return runCatching {
            val configRetrofitModel = configRetrofitDatasource.recoverToken(
                entity.entityToRetrofitModel()
            ).getOrThrow()
            configRetrofitModel.retrofitToEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun save(entity: Config): EmptyResult {
        return runCatching {
            val sharedPreferencesModel = entity.entityToSharedPreferencesModel()
            configSharedPreferencesDatasource.save(sharedPreferencesModel).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult {
        return runCatching {
            configSharedPreferencesDatasource.setFlagUpdate(flagUpdate).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getNumber(): Result<Long> {
        return runCatching {
            configSharedPreferencesDatasource.getNumber().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setStatusSend(statusSend: StatusSend): EmptyResult {
        return runCatching {
            configSharedPreferencesDatasource.setStatusSend(statusSend).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdTurnCheckListLast(): Result<Int?> {
        return runCatching {
            configSharedPreferencesDatasource.getIdTurnCheckListLast().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getDateCheckListLast(): Result<Date> {
        return runCatching {
            configSharedPreferencesDatasource.getDateCheckListLast().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}