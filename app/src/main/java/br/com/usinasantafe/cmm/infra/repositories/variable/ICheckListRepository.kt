package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.HeaderCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ICheckListRepository @Inject constructor(
    private val headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource
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

}