package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.retrofitToEntity
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
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
        try {
            val result = configSharedPreferencesDatasource.get()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.sharedPreferencesModelToEntity())
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        val result = configSharedPreferencesDatasource.getFlagUpdate()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getPassword(): Result<String> {
        val result = configSharedPreferencesDatasource.getPassword()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun hasConfig(): Result<Boolean> {
        val result = configSharedPreferencesDatasource.has()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun send(entity: Config): Result<Config> {
        try {
            val result = configRetrofitDatasource.recoverToken(
                entity.entityToRetrofitModel()
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val configRetrofitModel = result.getOrNull()!!
            val entity = configRetrofitModel.retrofitToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(entity: Config): Result<Boolean> {
        try {
            val sharedPreferencesModel = entity.entityToSharedPreferencesModel()
            val result = configSharedPreferencesDatasource.save(sharedPreferencesModel)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        val result = configSharedPreferencesDatasource.setFlagUpdate(flagUpdate)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getNumber(): Result<Long> {
        val result = configSharedPreferencesDatasource.getNumber()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean> {
        val result = configSharedPreferencesDatasource.setStatusSend(statusSend)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdEquip(): Result<Int> {
        val result = configSharedPreferencesDatasource.getIdEquip()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdTurnCheckListLast(): Result<Int?> {
        val result = configSharedPreferencesDatasource.getIdTurnCheckListLast()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getDateCheckListLast(): Result<Date> {
        val result = configSharedPreferencesDatasource.getDateCheckListLast()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getNroEquip(): Result<Long> {
        val result = configSharedPreferencesDatasource.getNroEquip()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }
}