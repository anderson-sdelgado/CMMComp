package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.retrofitToEntity
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import java.util.Date
import javax.inject.Inject

class IConfigRepository @Inject constructor(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource,
): ConfigRepository {

    override suspend fun get(): Result<Config> =
        call(getClassAndMethod()) {
            val config = configSharedPreferencesDatasource.get().getOrThrow() ?: return@call Config()
            config.sharedPreferencesModelToEntity()
        }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.getFlagUpdate().getOrThrow()
        }

    override suspend fun getPassword(): Result<String> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.getPassword().getOrThrow()
        }

    override suspend fun hasConfig(): Result<Boolean> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.has().getOrThrow()
        }

    override suspend fun send(entity: Config): Result<Config> =
        call(getClassAndMethod()) {
            val configRetrofitModel = configRetrofitDatasource.recoverToken(
                entity.entityToRetrofitModel()
            ).getOrThrow()
            configRetrofitModel.retrofitToEntity()
        }

    override suspend fun save(entity: Config): EmptyResult =
        call(getClassAndMethod()) {
            val sharedPreferencesModel = entity.entityToSharedPreferencesModel()
            configSharedPreferencesDatasource.save(sharedPreferencesModel).getOrThrow()
        }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): EmptyResult =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.setFlagUpdate(flagUpdate).getOrThrow()
        }

    override suspend fun getNumber(): Result<Long> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.getNumber().getOrThrow()
        }

    override suspend fun setStatusSend(statusSend: StatusSend): EmptyResult =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.setStatusSend(statusSend).getOrThrow()
        }

    override suspend fun getIdTurnCheckListLast(): Result<Int?> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.getIdTurnCheckListLast().getOrThrow()
        }

    override suspend fun getDateCheckListLast(): Result<Date> =
        call(getClassAndMethod()) {
            configSharedPreferencesDatasource.getDateCheckListLast().getOrThrow()
        }

}