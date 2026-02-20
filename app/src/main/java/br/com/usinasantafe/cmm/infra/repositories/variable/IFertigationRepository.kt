package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Collection
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.infra.datasource.room.variable.CollectionRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.CollectionRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.call
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFertigationRepository @Inject constructor(
    private val headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource,
    private val itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource,
    private val collectionRoomDatasource: CollectionRoomDatasource,
): FertigationRepository {

    override suspend fun setIdEquipMotorPump(idEquip: Int): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(idEquip).getOrThrow()
        }

    override suspend fun setIdNozzle(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.setIdNozzle(id).getOrThrow()
        }

    override suspend fun getIdNozzle(): Result<Int> =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.getIdNozzle().getOrThrow()
        }

    override suspend fun setValuePressure(value: Double): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.setValuePressure(value).getOrThrow()
        }

    override suspend fun getValuePressure(): Result<Double> =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.getValuePressure().getOrThrow()
        }

    override suspend fun setSpeedPressure(speed: Int): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.setSpeedPressure(speed).getOrThrow()
        }

    override suspend fun initialCollection(
        nroOS: Int,
        idHeader: Int
    ): Result<Unit> =
        call(getClassAndMethod()) {
            collectionRoomDatasource.insert(
                CollectionRoomModel(
                    nroOS = nroOS,
                    idHeader = idHeader
                )
            ).getOrThrow()
        }

    override suspend fun hasCollectionByIdHeaderAndValueNull(idHeader: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            collectionRoomDatasource.hasByIdHeaderAndValueNull(idHeader).getOrThrow()
        }

    override suspend fun listCollectionByIdHeader(idHeader: Int): Result<List<Collection>> =
        call(getClassAndMethod()) {
            collectionRoomDatasource.listByIdHeader(idHeader).getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun updateCollection(
        id: Int,
        value: Double
    ): EmptyResult =
        call(getClassAndMethod()) {
            collectionRoomDatasource.update(id, value).getOrThrow()
        }

}