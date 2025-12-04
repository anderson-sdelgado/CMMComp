package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityEquipToRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IEquipRepository @Inject constructor(
    private val equipRetrofitDatasource: EquipRetrofitDatasource,
    private val equipRoomDatasource: EquipRoomDatasource
): EquipRepository {

    override suspend fun addAll(list: List<Equip>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityEquipToRoomModel() }
            val result = equipRoomDatasource.addAll(roomModelList)
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

    override suspend fun deleteAll(): Result<Boolean> {
        val result = equipRoomDatasource.deleteAll()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun listByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<Equip>> {
        try {
            val result = equipRetrofitDatasource.listByIdEquip(
                token = token,
                idEquip = idEquip
            )
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getDescrByIdEquip(
        idEquip: Int
    ): Result<String> {
        val result = equipRoomDatasource.getDescrByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getCodTurnEquipByIdEquip(
        idEquip: Int
    ): Result<Int> {
        val result = equipRoomDatasource.getCodTurnEquipByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getHourMeterByIdEquip(idEquip: Int): Result<Double> {
        val result = equipRoomDatasource.getHourMeterByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun updateHourMeterByIdEquip(
        hourMeter: Double,
        idEquip: Int
    ): Result<Boolean> {
        val result = equipRoomDatasource.updateHourMeterByIdEquip(
            hourMeter = hourMeter,
            idEquip = idEquip
        )
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getTypeEquipByIdEquip(idEquip: Int): Result<TypeEquip> {
        val result = equipRoomDatasource.getTypeEquipByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdCheckListByIdEquip(idEquip: Int): Result<Int> {
        val result = equipRoomDatasource.getIdCheckListByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getFlagMechanicByIdEquip(idEquip: Int): Result<Boolean> {
        val result = equipRoomDatasource.getFlagMechanicByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getFlagTireByIdEquip(idEquip: Int): Result<Boolean> {
        val result = equipRoomDatasource.getFlagTireByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }


}