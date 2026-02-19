package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.CollectionDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.CollectionRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.CollectionRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class ICollectionRoomDatasource @Inject constructor(
    private val collectionDao: CollectionDao
): CollectionRoomDatasource {

    override suspend fun insert(model: CollectionRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            val resultHas = collectionDao.hasByIdHeaderAndNroOS(
                idHeader = model.idHeader,
                nroOS = model.nroOS
            )
            if (!resultHas) collectionDao.insert(model)
        }

}