package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.NoteMotoMec
import br.com.usinasantafe.cmm.domain.errors.failure
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.FlowComposting
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMotoMecRepository @Inject constructor(
    private val headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource,
    private val headerMotoMecRoomDatasource: HeaderMotoMecRoomDatasource,
    private val noteMotoMecSharedPreferencesDatasource: NoteMotoMecSharedPreferencesDatasource,
    private val noteMotoMecRoomDatasource: NoteMotoMecRoomDatasource,
    private val motoMecRetrofitDatasource: MotoMecRetrofitDatasource
): MotoMecRepository {

    override suspend fun setRegOperatorHeader(regOperator: Int): Result<Boolean> {
        val resultClean = headerMotoMecSharedPreferencesDatasource.clean()
        resultClean.onFailure { return Result.failure(it) }
        val result = headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun setDataEquipHeader(
        idEquip: Int,
        typeEquip: TypeEquip
    ): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setDataEquip(
            idEquip = idEquip,
            typeEquip = typeEquip
        )
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun setIdTurnHeader(idTurn: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun setNroOSHeader(nroOS: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getNroOSHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getNroOS()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun setIdActivityHeader(idActivity: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getIdActivityHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getIdActivity()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getIdEquipHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getIdEquip()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setHourMeter(hourMeter)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun saveHeader(): Result<Boolean> {
        try {
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            resultGet.onFailure { return Result.failure(it) }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entity.entityToRoomModel()
            val resultAdd = headerMotoMecRoomDatasource.save(modelRoom)
            resultAdd.onFailure { return Result.failure(it) }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkHeaderOpen(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkHeaderOpen()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getIdByHeaderOpen(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getIdByHeaderOpen()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun setHourMeterFinishHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.setHourMeterFinish(hourMeter)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun finishHeader(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.finish()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun checkHeaderSend(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkHeaderSend()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun setStatusConHeader(status: Boolean): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setStatusCon(status)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getIdTurnHeader(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getIdTurnByHeaderOpen()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getRegOperatorHeader(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getRegOperatorOpen()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getCompostingHeader(): Result<FlowComposting> {
        TODO("Not yet implemented")
    }

    override suspend fun setNroOSNote(nroOS: Int): Result<Boolean> {
        try {
            val resultClean = noteMotoMecSharedPreferencesDatasource.clean()
            resultClean.onFailure { return Result.failure(it) }
            val resultGetStatusCon = headerMotoMecRoomDatasource.getStatusConByHeaderOpen()
            resultGetStatusCon.onFailure { return Result.failure(it) }
            val statusCon = resultGetStatusCon.getOrNull()!!
            val result = noteMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
                nroOS = nroOS,
                statusCon = statusCon
            )
            result.onFailure { return Result.failure(it) }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdActivityNote(id: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setIdActivity(id)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun saveNote(idHeader: Int): Result<Boolean> {
        try {
            val resultGet = noteMotoMecSharedPreferencesDatasource.get()
            resultGet.onFailure { return Result.failure(it) }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val result = noteMotoMecRoomDatasource.save(
                entity.entityToRoomModel(
                    idHeader = idHeader
                )
            )
            result.onFailure { return Result.failure(it) }
            val resultSetSend = headerMotoMecRoomDatasource.setSendHeader(idHeader)
            resultSetSend.onFailure { return Result.failure(it) }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdActivityNote(): Result<Int> {
        val result = noteMotoMecSharedPreferencesDatasource.getIdActivity()
        result.onFailure { return Result.failure(it) }
        return result

    }

    override suspend fun setIdStop(id: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setIdStop(id)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun hasNoteByIdHeader(idHeader: Int): Result<Boolean> {
        val result = noteMotoMecRoomDatasource.checkHasByIdHeader(idHeader)
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun send(
        number: Long,
        token: String
    ): Result<Boolean> {
        try {
            val resultListHeaderSend = headerMotoMecRoomDatasource.listHeaderSend()
            resultListHeaderSend.onFailure { return Result.failure(it) }
            val modelRetrofitList =
                resultListHeaderSend.getOrNull()!!.map {
                    val resultListNoteSend = noteMotoMecRoomDatasource.listByIdHeaderAndSend(it.id!!)
                    resultListNoteSend.onFailure { return Result.failure(it) }
                    val noteMotoMecList = resultListNoteSend.getOrNull()!!
                    it.roomModelToRetrofitModel(
                        number = number,
                        noteMotoMecList = noteMotoMecList.map { note -> note.roomModelToRetrofitModel() }
                    )
                }
            val resultSend = motoMecRetrofitDatasource.send(
                token = token,
                modelList = modelRetrofitList
            )
            resultSend.onFailure { return Result.failure(it) }
            val headerMotoMecRetrofitList = resultSend.getOrNull()!!
            for(headerMotoMec in headerMotoMecRetrofitList){
                val noteMotoMecRetrofitList = headerMotoMec.noteMotoMecList
                for(noteMotoMec in noteMotoMecRetrofitList){
                    val result = noteMotoMecRoomDatasource.setSentNote(
                        id = noteMotoMec.id,
                        idServ = noteMotoMec.idServ
                    )
                    result.onFailure { return Result.failure(it) }
                }
                val result = headerMotoMecRoomDatasource.setSentHeader(
                    id = headerMotoMec.id,
                    idServ = headerMotoMec.idServ
                )
                result.onFailure { return Result.failure(it) }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun noteListByIdHeader(idHeader: Int): Result<List<NoteMotoMec>> {
        try {
            val resultNoteList = noteMotoMecRoomDatasource.listByIdHeader(idHeader)
            resultNoteList.onFailure { return Result.failure(it) }
            val list = resultNoteList.getOrNull()!!
            return Result.success(list.map { it.roomModelToEntity() })
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun hasNoteByIdStopAndIdHeader(
        idHeader: Int,
        idStop: Int
    ): Result<Boolean> {
        val result = noteMotoMecRoomDatasource.hasByIdStopAndIdHeader(
            idStop,
            idHeader
        )
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun getNoteLastByIdHeader(idHeader: Int): Result<NoteMotoMec> {
        TODO("Not yet implemented")
    }


}