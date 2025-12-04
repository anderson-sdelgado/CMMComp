package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

interface SetRespItemCheckList {
    suspend operator fun invoke(
        pos: Int,
        id: Int,
        option: OptionRespCheckList
    ): Result<Boolean>
}

class ISetRespItemCheckList @Inject constructor(
    private val checkListRepository: CheckListRepository,
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository,
    private val itemCheckListRepository: ItemCheckListRepository,
    private val startWorkManager: StartWorkManager
): SetRespItemCheckList {

    override suspend fun invoke(
        pos: Int,
        id: Int,
        option: OptionRespCheckList
    ): Result<Boolean> {
        try {
            if(pos == 1) {
                val resultClean = checkListRepository.cleanResp()
                resultClean.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
            }
            val entity = RespItemCheckList(
                idItem = id,
                option = option
            )
            val resultSave = checkListRepository.saveResp(entity)
            resultSave.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val resultGetIdEquip = configRepository.getIdEquip()
            resultGetIdEquip.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idEquip = resultGetIdEquip.getOrNull()!!
            val resultGetIdCheckList = equipRepository.getIdCheckListByIdEquip(idEquip)
            resultGetIdCheckList.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val idCheckList = resultGetIdCheckList.getOrNull()!!
            val resultCount = itemCheckListRepository.countByIdCheckList(idCheckList)
            resultCount.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val count = resultCount.getOrNull()!!
            val check =  pos < count
            if(!check) {
                val resultSave = checkListRepository.saveCheckList()
                resultSave.onFailure {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = it
                    )
                }
                startWorkManager()
            }
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}