package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.RespItemCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
    private val headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource,
    private val respItemCheckListSharedPreferencesDatasource: RespItemCheckListSharedPreferencesDatasource
): CheckListRepository {

    override suspend fun saveHeader(entity: HeaderCheckList): Result<Boolean> {
        try {
            val resultClean = headerCheckListSharedPreferencesDatasource.clean()
            if(resultClean.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultClean.exceptionOrNull()!!
                )
            }
            val model = entity.entityToSharedPreferencesModel()
            val resultSave = headerCheckListSharedPreferencesDatasource.save(model)
            if(resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return resultSave
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun clearResp(): Result<Boolean> {
        val result = respItemCheckListSharedPreferencesDatasource.clean()
        if(result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun saveResp(entity: RespItemCheckList): Result<Boolean> {
        try {
            val result = respItemCheckListSharedPreferencesDatasource.save(
                model = entity.entityToSharedPreferencesModel()
            )
            if(result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
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

    override suspend fun saveCheckList(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}