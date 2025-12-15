package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityEquipToRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IEquipRepository @Inject constructor(
    private val equipRetrofitDatasource: EquipRetrofitDatasource,
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource
): EquipRepository {

    override suspend fun saveEquipMain(entity: Equip): Result<Boolean> {
        try{
            val result = equipSharedPreferencesDatasource.save(entity.entityToSharedPreferencesModel())
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

    override suspend fun listAll(
        token: String
    ): Result<List<Equip>> {
        try {
            val result = equipRetrofitDatasource.listAll(
                token = token
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

    override suspend fun getIdEquipMain(): Result<Int> {
        val result = equipSharedPreferencesDatasource.getId()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getNroEquipMain(): Result<Long> {
        val result = equipSharedPreferencesDatasource.getNro()
        result.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        return result
    }

    override suspend fun getDescrByIdEquip(
        idEquip: Int
    ): Result<String> {
        val resultGetId = equipSharedPreferencesDatasource.getId()
        resultGetId.onFailure {
            return resultFailure(
                context = getClassAndMethod(),
                cause = it
            )
        }
        val idEquipSharedPreferences = resultGetId.getOrNull()!!
        if(idEquip == idEquipSharedPreferences) {
            val result = equipSharedPreferencesDatasource.getDescr()
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        }
        val result = equipRoomDatasource.getDescrByIdEquip(idEquip)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getCodTurnEquip(): Result<Int> {
        val result = equipSharedPreferencesDatasource.getCodTurnEquip()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getHourMeter(): Result<Double> {
        val result = equipSharedPreferencesDatasource.getHourMeter()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun updateHourMeter(
        hourMeter: Double
    ): Result<Boolean> {
        val result = equipSharedPreferencesDatasource.updateHourMeter(hourMeter)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getTypeEquip(): Result<TypeEquipMain> {
        val result = equipSharedPreferencesDatasource.getTypeEquip()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getIdCheckList(): Result<Int> {
        val result = equipSharedPreferencesDatasource.getIdCheckList()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getFlagMechanic(): Result<Boolean> {
        val result = equipSharedPreferencesDatasource.getFlagMechanic()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun getFlagTire(): Result<Boolean> {
        val result = equipSharedPreferencesDatasource.getFlagTire()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun hasEquipSecondary(
        nroEquip: Long,
        typeEquip: TypeEquipSecondary
    ): Result<Boolean> {
        val result = equipRoomDatasource.hasByNroEquipAndTypeEquip(
            nroEquip = nroEquip,
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


}