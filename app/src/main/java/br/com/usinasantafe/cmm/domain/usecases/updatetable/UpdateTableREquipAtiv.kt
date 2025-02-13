package br.com.usinasantafe.cmm.domain.usecases.updatetable

import javax.inject.Inject

interface UpdateTableREquipAtiv {
    suspend operator fun invoke(): Result<Boolean>
}

class IUpdateTableREquipAtiv @Inject constructor(): UpdateTableREquipAtiv {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}