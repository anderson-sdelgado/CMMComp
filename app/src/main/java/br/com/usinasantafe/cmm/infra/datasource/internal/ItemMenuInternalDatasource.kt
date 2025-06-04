package br.com.usinasantafe.cmm.infra.datasource.internal

import br.com.usinasantafe.cmm.infra.models.internal.ItemMenuInternalModel

interface ItemMenuInternalDatasource {
    suspend fun listAll(): Result<List<ItemMenuInternalModel>>
}