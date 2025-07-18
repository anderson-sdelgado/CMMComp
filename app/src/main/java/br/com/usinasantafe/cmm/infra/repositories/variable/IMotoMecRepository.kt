package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.domain.errors.resultFailureMiddle
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
        if (resultClean.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = resultClean.exceptionOrNull()!!
            )
        }
        val result = headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
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
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setIdTurnHeader(idTurn: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setNroOSHeader(nroOS: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getNroOSHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getNroOS()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setIdActivityHeader(idActivity: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getIdEquipHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getIdEquip()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setHourMeter(hourMeter)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun saveHeader(): Result<Boolean> {
        try {
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entity.entityToRoomModel()
            val resultAdd = headerMotoMecRoomDatasource.save(modelRoom)
            if(resultAdd.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultAdd.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkHeaderOpen(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkHeaderOpen()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getIdByHeaderOpen(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getIdByHeaderOpen()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setHourMeterFinishHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.setHourMeterFinish(hourMeter)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun finishHeader(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.finish()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun checkHeaderSend(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkHeaderSend()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setStatusConHeader(status: Boolean): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setStatusCon(status)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getIdTurnHeader(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getIdTurnByHeaderOpen()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getRegOperator(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getRegOperatorOpen()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setNroOSNote(nroOS: Int): Result<Boolean> {
        try {
            val resultClean = noteMotoMecSharedPreferencesDatasource.clean()
            if(resultClean.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultClean.exceptionOrNull()!!
                )
            }
            val resultGetStatusCon = headerMotoMecRoomDatasource.getStatusConByHeaderOpen()
            if(resultGetStatusCon.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGetStatusCon.exceptionOrNull()!!
                )
            }
            val statusCon = resultGetStatusCon.getOrNull()!!
            val result = noteMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
                nroOS = nroOS,
                statusCon = statusCon
            )
            if(result.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdActivityNote(id: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setIdActivity(id)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun saveNote(idHeader: Int): Result<Boolean> {
        try {
            val resultGet = noteMotoMecSharedPreferencesDatasource.get()
            if (resultGet.isFailure) {
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
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
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val resultSetSend = headerMotoMecRoomDatasource.setSendHeader(idHeader)
            if(resultSetSend.isFailure){
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSetSend.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdActivity(): Result<Int> {
        val result = noteMotoMecSharedPreferencesDatasource.getIdActivity()
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result

    }

    override suspend fun setIdStop(id: Int): Result<Boolean> {
        val result = noteMotoMecSharedPreferencesDatasource.setIdStop(id)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun checkNoteHasByIdHeader(idHeader: Int): Result<Boolean> {
        val result = noteMotoMecRoomDatasource.checkHasByIdHeader(idHeader)
        if (result.isFailure) {
            return resultFailureMiddle(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
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
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultListHeaderSend.exceptionOrNull()!!
                )
            }
            val modelRetrofitList =
                resultListHeaderSend.getOrNull()!!.map {
                    val resultListNoteSend = noteMotoMecRoomDatasource.listByIdHeaderAndSend(it.id!!)
                    if (resultListNoteSend.isFailure) {
                        return resultFailureMiddle(
                            context = getClassAndMethod(),
                            cause = resultListNoteSend.exceptionOrNull()!!
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
                return resultFailureMiddle(
                    context = getClassAndMethod(),
                    cause = resultSend.exceptionOrNull()!!
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
                        return resultFailureMiddle(
                            context = getClassAndMethod(),
                            cause = result.exceptionOrNull()!!
                        )
                    }
                }
                val result = headerMotoMecRoomDatasource.setSentHeader(
                    id = headerMotoMec.id,
                    idBD = headerMotoMec.idBD
                )
                if(result.isFailure){
                    return resultFailureMiddle(
                        context = getClassAndMethod(),
                        cause = result.exceptionOrNull()!!
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}