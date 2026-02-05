package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.domain.entities.variable.Performance
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.PerformanceRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.variable.roomModelToSharedPreferences
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject
import kotlin.getOrThrow

class IMotoMecRepository @Inject constructor(
    private val headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource,
    private val headerMotoMecRoomDatasource: HeaderMotoMecRoomDatasource,
    private val itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource,
    private val itemMotoMecRoomDatasource: ItemMotoMecRoomDatasource,
    private val trailerSharedPreferencesDatasource: TrailerSharedPreferencesDatasource,
    private val performanceRoomDatasource: PerformanceRoomDatasource,
    private val motoMecRetrofitDatasource: MotoMecRetrofitDatasource
): MotoMecRepository {

    override suspend fun refreshHeaderOpen(): EmptyResult =
        call(getClassAndMethod()) {
            val model = headerMotoMecRoomDatasource.getOpen().getOrThrow()
            headerMotoMecSharedPreferencesDatasource.save(model.roomModelToSharedPreferences()).getOrThrow()
        }

    override suspend fun setRegOperatorHeader(regOperator: Int): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.clean().getOrThrow()
            headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator).getOrThrow()
        }

    override suspend fun setDataEquipHeader(
        idEquip: Int,
        typeEquip: TypeEquip
    ): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setDataEquip(idEquip, typeEquip).getOrThrow()
        }

    override suspend fun getTypeEquipHeader(): Result<TypeEquip> =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.getTypeEquip().getOrThrow()
        }

    override suspend fun setIdTurnHeader(idTurn: Int): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn).getOrThrow()
        }

    override suspend fun setNroOSHeader(nroOS: Int): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS).getOrThrow()
        }

    override suspend fun getNroOSHeader(): Result<Int> =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.getNroOS().getOrThrow()
        }

    override suspend fun setIdActivityHeader(idActivity: Int): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity).getOrThrow()
        }

    override suspend fun getIdActivityHeader(): Result<Int> =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.getIdActivity().getOrThrow()
        }

    override suspend fun getIdEquipHeader(): Result<Int> =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.getIdEquip().getOrThrow()
        }

    override suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Unit> =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setHourMeter(hourMeter).getOrThrow()
        }

    override suspend fun saveHeader(): EmptyResult =
        call(getClassAndMethod()) {
            val modelSharedPreferences = headerMotoMecSharedPreferencesDatasource.get().getOrThrow()
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = runCatching {
                entity.entityToRoomModel()
            }.getOrElse { e ->
                throw Exception(entity::entityToRoomModel.name, e)
            }
            val id = headerMotoMecRoomDatasource.save(modelRoom).getOrThrow()
            headerMotoMecSharedPreferencesDatasource.setId(id.toInt()).getOrThrow()
        }

    override suspend fun hasHeaderOpen(): Result<Boolean> =
        call(getClassAndMethod()) {
            headerMotoMecRoomDatasource.checkOpen().getOrThrow()
        }

    override suspend fun getIdByHeaderOpen(): Result<Int> =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.getId().getOrThrow()
        }

    override suspend fun setHourMeterFinishHeader(hourMeter: Double): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecRoomDatasource.setHourMeterFinish(hourMeter).getOrThrow()
        }

    override suspend fun setIdEquipMotorPumpHeader(idEquip: Int): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(idEquip).getOrThrow()
        }

    override suspend fun finishHeader(): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecRoomDatasource.finish().getOrThrow()
        }

    override suspend fun hasHeaderSend(): Result<Boolean> =
        call(getClassAndMethod()) {
            headerMotoMecRoomDatasource.hasSend().getOrThrow()
        }

    override suspend fun setStatusConHeader(status: Boolean): EmptyResult =
        call(getClassAndMethod()) {
            headerMotoMecSharedPreferencesDatasource.setStatusCon(status).getOrThrow()
        }

    override suspend fun getIdTurnHeader(): Result<Int> =
        call(getClassAndMethod()) {
            headerMotoMecRoomDatasource.getIdTurn().getOrThrow()
        }

    override suspend fun getRegOperatorHeader(): Result<Int> =
        call(getClassAndMethod()) {
            headerMotoMecRoomDatasource.getRegOperator().getOrThrow()
        }

    override suspend fun getFlowCompostingHeader(): Result<FlowComposting> =
        call(getClassAndMethod()) {
            headerMotoMecRoomDatasource.getFlowComposting().getOrThrow()
        }

    override suspend fun setNroOSNote(nroOS: Int): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.clean().getOrThrow()
            val statusCon = headerMotoMecSharedPreferencesDatasource.getStatusCon().getOrThrow()
            itemMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(nroOS, statusCon).getOrThrow()
        }

    override suspend fun setIdActivityNote(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.setIdActivity(id).getOrThrow()
        }

    override suspend fun setNroEquipTranshipmentNote(nroEquipTranshipment: Long): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.setNroEquipTranshipment(nroEquipTranshipment).getOrThrow()
        }

    override suspend fun saveNote(idHeader: Int): EmptyResult =
        call(getClassAndMethod()) {
            val modelSharedPreferences = itemMotoMecSharedPreferencesDatasource.get().getOrThrow()
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = runCatching {
                entity.entityToRoomModel(idHeader = idHeader)
            }.getOrElse { e ->
                throw Exception(entity::entityToRoomModel.name, e)
            }
            itemMotoMecRoomDatasource.save(modelRoom).getOrThrow()
            headerMotoMecRoomDatasource.setSend(idHeader).getOrThrow()
        }

    override suspend fun getIdActivityNote(): Result<Int> =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.getIdActivity().getOrThrow()
        }

    override suspend fun setIdStop(id: Int): EmptyResult =
        call(getClassAndMethod()) {
            itemMotoMecSharedPreferencesDatasource.setIdStop(id).getOrThrow()
        }

    override suspend fun hasNoteByIdHeader(idHeader: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            itemMotoMecRoomDatasource.hasByIdHeader(idHeader).getOrThrow()
        }

    override suspend fun send(
        number: Long,
        token: String
    ): EmptyResult =
        call(getClassAndMethod()) {
            val resultListHeaderSend = headerMotoMecRoomDatasource.listSend().getOrThrow()
            val modelRetrofitList =
                resultListHeaderSend.map {
                    val noteMotoMecList = itemMotoMecRoomDatasource.listByIdHeaderAndSend(it.id!!).getOrThrow()
                    it.roomModelToRetrofitModel(
                        number = number,
                        noteMotoMecList = noteMotoMecList.map { note -> note.roomModelToRetrofitModel() }
                    )
                }
            val headerMotoMecRetrofitList = motoMecRetrofitDatasource.send(
                token = token,
                modelList = modelRetrofitList
            ).getOrThrow()
            for(headerMotoMec in headerMotoMecRetrofitList){
                val noteMotoMecRetrofitList = headerMotoMec.noteMotoMecList
                for(noteMotoMec in noteMotoMecRetrofitList){
                    itemMotoMecRoomDatasource.setSentNote(
                        id = noteMotoMec.id,
                        idServ = noteMotoMec.idServ
                    ).getOrThrow()
                }
                headerMotoMecRoomDatasource.setSent(
                    id = headerMotoMec.id,
                    idServ = headerMotoMec.idServ
                ).getOrThrow()
            }
        }

    override suspend fun listNoteByIdHeader(idHeader: Int): Result<List<ItemMotoMec>> =
        call(getClassAndMethod()) {
            itemMotoMecRoomDatasource.listByIdHeader(idHeader).getOrThrow().map{ it.roomModelToEntity() }
        }

    override suspend fun hasNoteByIdStopAndIdHeader(
        idHeader: Int,
        idStop: Int
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            itemMotoMecRoomDatasource.hasByIdStopAndIdHeader(idStop, idHeader).getOrThrow()
        }

    override suspend fun getNoteLastByIdHeader(idHeader: Int): Result<ItemMotoMec> =
        call(getClassAndMethod()) {
            itemMotoMecRoomDatasource.getLastByIdHeader(idHeader).getOrThrow().roomModelToEntity()
        }

    override suspend fun hasCouplingTrailerImplement(): Result<Boolean> =
        call(getClassAndMethod()) {
            trailerSharedPreferencesDatasource.has().getOrThrow()
        }

    override suspend fun uncouplingTrailerImplement(): EmptyResult =
        call(getClassAndMethod()) {
            trailerSharedPreferencesDatasource.clean().getOrThrow()
        }

    override suspend fun insertInitialPerformance(): EmptyResult =
        call(getClassAndMethod()) {
            val headerModel = headerMotoMecSharedPreferencesDatasource.get().getOrThrow()
            performanceRoomDatasource.insert(
                PerformanceRoomModel(
                    nroOS = headerModel.nroOS!!,
                    idHeader = headerModel.id!!,
                )
            ).getOrThrow()
        }

    override suspend fun listPerformanceByIdHeader(idHeader: Int): Result<List<Performance>> =
        call(getClassAndMethod()) {
            performanceRoomDatasource.listByIdHeader(idHeader).getOrThrow().map { it.roomModelToEntity() }
        }

    override suspend fun updatePerformance(
        id: Int,
        value: Double
    ): EmptyResult =
        call(getClassAndMethod()) {
            performanceRoomDatasource.update(id, value).getOrThrow()
        }

    override suspend fun getNroOSPerformanceById(id: Int): Result<Int> =
        call(getClassAndMethod()) {
            performanceRoomDatasource.getNroOSById(id).getOrThrow()
        }

    override suspend fun hasPerformanceByIdHeaderAndValueNull(idHeader: Int): Result<Boolean> =
        call(getClassAndMethod()) {
            performanceRoomDatasource.hasByIdHeaderAndValueNull(idHeader).getOrThrow()
        }

}