package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
import java.util.Date
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
            headerMotoMecSharedPreferencesDatasource.save(model.roomModelToSharedPreferences()).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun setRegOperatorHeader(regOperator: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.clean().getOrThrow()
            headerMotoMecSharedPreferencesDatasource.setRegOperator(regOperator).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
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
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun getTypeEquipHeader(): Result<TypeEquip> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getTypeEquip().getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun setIdTurnHeader(idTurn: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setIdTurn(idTurn).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun setNroOSHeader(nroOS: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setNroOS(nroOS).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun getNroOSHeader(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getNroOS().getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun setIdActivityHeader(idActivity: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setIdActivity(idActivity).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun getIdActivityHeader(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getIdActivity().getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun getIdEquipHeader(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getIdEquip().getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Unit> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setHourMeter(hourMeter).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun saveHeader(): EmptyResult {
        return runCatching {
            val modelSharedPreferences = headerMotoMecSharedPreferencesDatasource.get().getOrThrow()
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            val modelRoom = entity.entityToRoomModel()
            val id = headerMotoMecRoomDatasource.save(modelRoom).getOrThrow()
            headerMotoMecSharedPreferencesDatasource.setId(id.toInt()).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun hasHeaderOpen(): Result<Boolean> {
        return runCatching {
            headerMotoMecRoomDatasource.checkOpen().getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun getIdByHeaderOpen(): Result<Int> {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.getId().getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun setHourMeterFinishHeader(hourMeter: Double): EmptyResult {
        return runCatching {
            headerMotoMecRoomDatasource.setHourMeterFinish(hourMeter).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
    }

    override suspend fun setIdEquipMotorPumpHeader(idEquip: Int): EmptyResult {
        return runCatching {
            headerMotoMecSharedPreferencesDatasource.setIdEquipMotorPump(idEquip).getOrThrow()
        }.onFailure {
            resultFailure(context = getClassAndMethod(), cause = it)
        }
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

    override suspend fun hasHeaderSend(): Result<Boolean> {
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
            val resultGetStatusCon = headerMotoMecSharedPreferencesDatasource.getStatusCon()
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

    override suspend fun setIdActivityNote(id: Int): EmptyResult {
        val result = itemMotoMecSharedPreferencesDatasource.setIdActivity(id)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun setNroEquipTranshipmentNote(nroEquipTranshipment: Long): Result<Boolean> {
        val result = itemMotoMecSharedPreferencesDatasource.setNroEquipTranshipment(nroEquipTranshipment)
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

    override suspend fun saveNote(idHeader: Int): EmptyResult {
        return runCatching {

            val modelSharedPreferences = itemMotoMecSharedPreferencesDatasource.get().getOrThrow()
            val entity = modelSharedPreferences.sharedPreferencesModelToEntity()
            itemMotoMecRoomDatasource.save(
                entity.entityToRoomModel(
                    idHeader = idHeader
                )
            ).getOrThrow()
            headerMotoMecRoomDatasource.setSend(idHeader).getOrThrow()
            return Result.success(Unit)

        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
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

    override suspend fun setIdStop(id: Int): EmptyResult {
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

    override suspend fun uncouplingTrailerImplement(): Result<Boolean> {
        val result = trailerSharedPreferencesDatasource.clean()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

    override suspend fun insertInitialPerformance(): Result<Unit> {
        try {
            val resultHeaderSharedPreferences = headerMotoMecSharedPreferencesDatasource.get()
            resultHeaderSharedPreferences.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val headerModel = resultHeaderSharedPreferences.getOrNull()!!
            val resultInsert = performanceRoomDatasource.insert(
                PerformanceRoomModel(
                    nroOS = headerModel.nroOS!!,
                    idHeader = headerModel.id!!,
                )
            )
            resultInsert.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return resultInsert
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}