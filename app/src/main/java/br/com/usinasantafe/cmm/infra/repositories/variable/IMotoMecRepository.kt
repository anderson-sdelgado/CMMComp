package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.TrailerSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.roomModelToRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IMotoMecRepository @Inject constructor(
    private val headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource,
    private val headerMotoMecRoomDatasource: HeaderMotoMecRoomDatasource,
    private val itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource,
    private val itemMotoMecRoomDatasource: ItemMotoMecRoomDatasource,
    private val trailerSharedPreferencesDatasource: TrailerSharedPreferencesDatasource,
    private val motoMecRetrofitDatasource: MotoMecRetrofitDatasource
): MotoMecRepository {

    override suspend fun setRegOperatorHeader(regOperator: Int): Result<Boolean> {
        val resultClean = headerMotoMecSharedPreferencesDatasource.clean()
        resultClean.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        val result = headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setIdTurnHeader(idTurn: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setNroOSHeader(nroOS: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getNroOSHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getNroOS()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setIdActivityHeader(idActivity: Int): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdActivityHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getIdActivity()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdEquipHeader(): Result<Int> {
        val result = headerMotoMecSharedPreferencesDatasource.getIdEquip()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setHourMeter(hourMeter)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun saveHeader(): Result<Boolean> {
        try {
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entity.entityToRoomModel()
            val resultAdd = headerMotoMecRoomDatasource.save(modelRoom)
            resultAdd.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkHeaderOpen(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkOpen()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdByHeaderOpen(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getId()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setHourMeterFinishHeader(hourMeter: Double): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.setHourMeterFinish(hourMeter)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun finishHeader(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.finish()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun checkHeaderSend(): Result<Boolean> {
        val result = headerMotoMecRoomDatasource.checkSend()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setStatusConHeader(status: Boolean): Result<Boolean> {
        val result = headerMotoMecSharedPreferencesDatasource.setStatusCon(status)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdTurnHeader(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getIdTurn()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getRegOperatorHeader(): Result<Int> {
        val result = headerMotoMecRoomDatasource.getRegOperator()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getFlowCompostingHeader(): Result<FlowComposting> {
        val result = headerMotoMecRoomDatasource.getFlowComposting()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

    override suspend fun setNroOSNote(nroOS: Int): Result<Boolean> {
        try {
            val resultClean = itemMotoMecSharedPreferencesDatasource.clean()
            resultClean.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultGetStatusCon = headerMotoMecRoomDatasource.getStatusCon()
            resultGetStatusCon.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val statusCon = resultGetStatusCon.getOrNull()!!
            val result = itemMotoMecSharedPreferencesDatasource.setNroOSAndStatusCon(
                nroOS = nroOS,
                statusCon = statusCon
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdActivityNote(id: Int): Result<Boolean> {
        val result = itemMotoMecSharedPreferencesDatasource.setIdActivity(id)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun saveNote(idHeader: Int): Result<Boolean> {
        try {
            val resultGet = itemMotoMecSharedPreferencesDatasource.get()
            resultGet.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val modelSharedPreferences = resultGet.getOrNull()!!
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val result = itemMotoMecRoomDatasource.save(
                entity.entityToRoomModel(
                    idHeader = idHeader
                )
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultSetSend = headerMotoMecRoomDatasource.setSend(idHeader)
            resultSetSend.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdActivityNote(): Result<Int> {
        val result = itemMotoMecSharedPreferencesDatasource.getIdActivity()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result

    }

    override suspend fun setIdStop(id: Int): Result<Boolean> {
        val result = itemMotoMecSharedPreferencesDatasource.setIdStop(id)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun hasNoteByIdHeader(idHeader: Int): Result<Boolean> {
        val result = itemMotoMecRoomDatasource.checkHasByIdHeader(idHeader)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun send(
        number: Long,
        token: String
    ): Result<Boolean> {
        try {
            val resultListHeaderSend = headerMotoMecRoomDatasource.listSend()
            resultListHeaderSend.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val modelRetrofitList =
                resultListHeaderSend.getOrNull()!!.map {
                    val resultListNoteSend = itemMotoMecRoomDatasource.listByIdHeaderAndSend(it.id!!)
                    resultListNoteSend.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
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
            resultSend.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val headerMotoMecRetrofitList = resultSend.getOrNull()!!
            for(headerMotoMec in headerMotoMecRetrofitList){
                val noteMotoMecRetrofitList = headerMotoMec.noteMotoMecList
                for(noteMotoMec in noteMotoMecRetrofitList){
                    val result = itemMotoMecRoomDatasource.setSentNote(
                        id = noteMotoMec.id,
                        idServ = noteMotoMec.idServ
                    )
                    result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
                }
                val result = headerMotoMecRoomDatasource.setSent(
                    id = headerMotoMec.id,
                    idServ = headerMotoMec.idServ
                )
                result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun noteListByIdHeader(idHeader: Int): Result<List<ItemMotoMec>> {
        try {
            val resultNoteList = itemMotoMecRoomDatasource.listByIdHeader(idHeader)
            resultNoteList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
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
        val result = itemMotoMecRoomDatasource.hasByIdStopAndIdHeader(
            idStop,
            idHeader
        )
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getNoteLastByIdHeader(idHeader: Int): Result<ItemMotoMec> {
        val result = itemMotoMecRoomDatasource.getLastByIdHeader(idHeader)
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val entity = result.getOrNull()!!
        return Result.success(entity.roomModelToEntity())
    }

    override suspend fun hasCouplingTrailerImplement(): Result<Boolean> {
        val result = trailerSharedPreferencesDatasource.has()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }


}