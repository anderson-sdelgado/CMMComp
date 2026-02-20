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

    override suspend fun hasByIdHeaderAndValueNull(idHeader: Int): Result<Boolean> =
        result(getClassAndMethod()){
            collectionDao.hasByIdHeaderAndValueNull(idHeader)
        }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<CollectionRoomModel>> =
        result(getClassAndMethod()) {
            collectionDao.listByIdHeader(idHeader)
        }

    override suspend fun update(
        id: Int,
        value: Double
    ): EmptyResult =
        result(getClassAndMethod()){
            val model = collectionDao.getById(id)
            model.value = value
            collectionDao.update(model)
        }

}