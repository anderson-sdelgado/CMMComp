package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.utils.TypeEquip
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
        if (resultClean.isFailure) {
            val e = resultClean.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setRegOperator",
                message = e.message,
                cause = e.cause
            )
        }
        val result = headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setRegOperator",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setDataEquipHeader(
        idEquip: Int,
        typeEquip: TypeEquip
    ): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setDataEquip(
            idEquip = idEquip,
            typeEquip = typeEquip
        )
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setIdTurnHeader(idTurn: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setIdTurn",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun setNroOSHeader(nroOS: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun getNroOSHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getNroOS()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.getNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(result.getOrNull()!!)
    }

    override suspend fun setIdActivityHeader(idActivity: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun getIdEquipHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getIdEquip()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.getIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(result.getOrNull()!!)
    }

    override suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setHourMeter(hourMeter)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setHourMeterInitial",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun saveHeader(): Result<Boolean> {
        try {
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entity.entityToRoomModel()
            val resultAdd = headerMotoMecRoomDatasource.save(modelRoom)
            if(resultAdd.isFailure){
                val e = resultAdd.exceptionOrNull()!!
                return resultFailure(
                    context = "IMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IMotoMecRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkOpenHeader(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkHeaderOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.checkHeaderOpen",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getIdByOpenHeader(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getIdByHeaderOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.getIdByHeaderOpen",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun setHourMeterFinishHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.setHourMeterFinish(hourMeter)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setHourMeterFinish",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun finishHeader(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.finish()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.close",
                message = e.message,
                cause = e.cause
            )
        }
        return Result.success(true)
    }

    override suspend fun checkSendHeader(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkHeaderSend()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.checkHeaderSend",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun setStatusConHeader(status: Boolean): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setStatusCon(status)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setStatusConHeader",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getIdTurnHeader(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getIdTurnByHeaderOpen()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.getIdTurnHeader",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun setNroOSNote(nroOS: Int): Result<Boolean> {
        val resultClean = noteMotoMecSharedPreferencesDatasource.clean()
        if(resultClean.isFailure) {
            val e = resultClean.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        val resultGetStatusCon = headerMotoMecRoomDatasource.getStatusConByHeaderOpen()
        if(resultGetStatusCon.isFailure) {
            val e = resultGetStatusCon.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        val statusCon = resultGetStatusCon.getOrNull()!!
        val result = noteMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
            nroOS = nroOS,
            statusCon = statusCon
        )
        if(result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setNroOS",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun setIdActivityNote(id: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setIdActivity(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun saveNote(idHeader: Int): Result<Boolean> {
        try {
            val resultGet = noteMotoMecSharedPreferencesDatasource.get()
            if (resultGet.isFailure) {
                val e = resultGet.exceptionOrNull()!!
                return resultFailure(
                    context = "IMotoMecRepository.saveNote",
                    message = e.message,
                    cause = e.cause
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val result = noteMotoMecRoomDatasource.save(
                entity.entityToRoomModel(
                    idHeader = idHeader
                )
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            val resultSetSend = headerMotoMecRoomDatasource.setSendHeader(idHeader)
            if(resultSetSend.isFailure){
                val e = resultSetSend.exceptionOrNull()!!
                return resultFailure(
                    context = "IMotoMecRepository.save",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = "IMotoMecRepository.save",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdActivity(): Result<Int> {
        val result = noteMotoMecSharedPreferencesDatasource.getIdActivity()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.getIdActivity",
                message = e.message,
                cause = e.cause
            )
        }
        return result

    }

    override suspend fun setIdStop(id: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setIdStop(id)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.setIdStop",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun checkNoteHasByIdHeader(idHeader: Int): Result<Boolean> {
        val result = noteMotoMecRoomDatasource.checkHasByIdHeader(idHeader)
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IMotoMecRepository.checkHasByIdHeader",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun send(
        number: Long,
        token: String
    ): Result<Boolean> {
        try {
            val resultListHeaderSend = headerMotoMecRoomDatasource.listHeaderSend()
            if (resultListHeaderSend.isFailure) {
                val e = resultListHeaderSend.exceptionOrNull()!!
                return resultFailure(
                    context = "IMotoMecRepository.send",
                    message = e.message,
                    cause = e.cause
                )
            }
            val modelRetrofitList =
                resultListHeaderSend.getOrNull()!!.map {
                    val resultListNoteSend = noteMotoMecRoomDatasource.listByIdHeaderAndSend(it.id!!)
                    if (resultListNoteSend.isFailure) {
                        val e = resultListNoteSend.exceptionOrNull()!!
                        return resultFailure(
                            context = "IMotoMecRepository.send",
                            message = e.message,
                            cause = e.cause
                        )
                    }
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
            if (resultSend.isFailure) {
                val e = resultSend.exceptionOrNull()!!
                return resultFailure(
                    context = "IMotoMecRepository.send",
                    message = e.message,
                    cause = e.cause
                )
            }
            val headerMotoMecRetrofitList = resultSend.getOrNull()!!
            for(headerMotoMec in headerMotoMecRetrofitList){
                val noteMotoMecRetrofitList = headerMotoMec.noteMotoMecList
                for(noteMotoMec in noteMotoMecRetrofitList){
                    val result = noteMotoMecRoomDatasource.setSentNote(
                        id = noteMotoMec.id,
                        idBD = noteMotoMec.idBD
                    )
                    if(result.isFailure){
                        val e = result.exceptionOrNull()!!
                        return resultFailure(
                            context = "IMotoMecRepository.send",
                            message = e.message,
                            cause = e.cause
                        )
                    }
                }
                val result = headerMotoMecRoomDatasource.setSentHeader(
                    id = headerMotoMec.id,
                    idBD = headerMotoMec.idBD
                )
                if(result.isFailure){
                    val e = result.exceptionOrNull()!!
                    return resultFailure(
                        context = "IMotoMecRepository.send",
                        message = e.message,
                        cause = e.cause
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IMotoMecRepository.send",
                message = "-",
                cause = e
            )
        }
    }

}