package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.lib.resultFailure
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
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
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

    override suspend fun refreshHeaderOpen(): EmptyResult {
        return runCatching {
            val model = headerMotoMecRoomDatasource.getOpen().getOrThrow()
            headerMotoMecSharedPreferencesDatasource.save(
                model.roomModelToSharedPreferences()
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setRegOperatorHeader(regOperator: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.clean().getOrThrow()
            headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setDataEquipHeader(
        idEquip: Int,
        typeEquip: TypeEquip
    ): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setDataEquip(
                idEquip = idEquip,
                typeEquip = typeEquip
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getTypeEquipHeader(): Result<TypeEquip> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getTypeEquip().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setIdTurnHeader(idTurn: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setNroOSHeader(nroOS: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getNroOSHeader(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getNroOS().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setIdActivityHeader(idActivity: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdActivityHeader(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getIdActivity().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdEquipHeader(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getIdEquip().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Unit> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setHourMeter(hourMeter).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun saveHeader(): EmptyResult {
        return runCatching {
            val modelSharedPreferences = headerMotoMecSharedPreferencesDatasource.get().getOrThrow()
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = runCatching {
                entity.entityToRoomModel()
            }.getOrElse { e ->
                throw Exception(entity::entityToRoomModel.name, e)
            }
            val id = headerMotoMecRoomDatasource.save(modelRoom).getOrThrow()
            headerMotoMecSharedPreferencesDatasource.setId(id.toInt()).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasHeaderOpen(): Result<Boolean> {
        return runCatching {
            headerMotoMecRoomDatasource.checkOpen().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdByHeaderOpen(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getId().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setHourMeterFinishHeader(hourMeter: Double): EmptyResult {
        return runCatching {
            headerMotoMecRoomDatasource.setHourMeterFinish(hourMeter).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setIdEquipMotorPumpHeader(idEquip: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(idEquip).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun finishHeader(): EmptyResult {
        return runCatching {
            headerMotoMecRoomDatasource.finish().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasHeaderSend(): Result<Boolean> {
        return runCatching {
            headerMotoMecRoomDatasource.checkSend().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setStatusConHeader(status: Boolean): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setStatusCon(status).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdTurnHeader(): Result<Int> {
        return runCatching {
            headerMotoMecRoomDatasource.getIdTurn().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getRegOperatorHeader(): Result<Int> {
        return runCatching {
            headerMotoMecRoomDatasource.getRegOperator().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getFlowCompostingHeader(): Result<FlowComposting> {
        return runCatching {
            headerMotoMecRoomDatasource.getFlowComposting().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setNroOSNote(nroOS: Int): Result<Boolean> {
        return runCatching {
            itemMotoMecSharedPreferencesDatasource.clean().getOrThrow()
            val statusCon = headerMotoMecSharedPreferencesDatasource.getStatusCon().getOrThrow()
            itemMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
                nroOS = nroOS,
                statusCon = statusCon
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setIdActivityNote(id: Int): EmptyResult {
        return runCatching {
            itemMotoMecSharedPreferencesDatasource.setIdActivity(id).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setNroEquipTranshipmentNote(nroEquipTranshipment: Long): EmptyResult {
        return runCatching {
            itemMotoMecSharedPreferencesDatasource.setNroEquipTranshipment(nroEquipTranshipment).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun saveNote(idHeader: Int): EmptyResult {
        return runCatching {
            val modelSharedPreferences = itemMotoMecSharedPreferencesDatasource.get().getOrThrow()
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = runCatching {
                entity.entityToRoomModel(idHeader = idHeader)
            }.getOrElse { e ->
                throw Exception(entity::entityToRoomModel.name, e)
            }
            itemMotoMecRoomDatasource.save(modelRoom).getOrThrow()
            headerMotoMecRoomDatasource.setSend(idHeader).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdActivityNote(): Result<Int> {
        return runCatching {
            itemMotoMecSharedPreferencesDatasource.getIdActivity().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun setIdStop(id: Int): EmptyResult {
        return runCatching {
            itemMotoMecSharedPreferencesDatasource.setIdStop(id).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasNoteByIdHeader(idHeader: Int): Result<Boolean> {
        return runCatching {
            itemMotoMecRoomDatasource.checkHasByIdHeader(idHeader).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun send(
        number: Long,
        token: String
    ): EmptyResult {
        return runCatching {
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
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun noteListByIdHeader(idHeader: Int): Result<List<ItemMotoMec>> {
        return runCatching {
            itemMotoMecRoomDatasource.listByIdHeader(idHeader).getOrThrow().map{ it.roomModelToEntity() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasNoteByIdStopAndIdHeader(
        idHeader: Int,
        idStop: Int
    ): Result<Boolean> {
        return runCatching {
            itemMotoMecRoomDatasource.hasByIdStopAndIdHeader(idStop, idHeader).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getNoteLastByIdHeader(idHeader: Int): Result<ItemMotoMec> {
        return runCatching {
            itemMotoMecRoomDatasource.getLastByIdHeader(idHeader).getOrThrow().roomModelToEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasCouplingTrailerImplement(): Result<Boolean> {
        return runCatching {
            trailerSharedPreferencesDatasource.has().getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun uncouplingTrailerImplement(): EmptyResult {
        return runCatching {
            trailerSharedPreferencesDatasource.clean().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun insertInitialPerformance(): EmptyResult {
        return runCatching {
            val headerModel = headerMotoMecSharedPreferencesDatasource.get().getOrThrow()
            performanceRoomDatasource.insert(
                PerformanceRoomModel(
                    nroOS = headerModel.nroOS!!,
                    idHeader = headerModel.id!!,
                )
            ).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}