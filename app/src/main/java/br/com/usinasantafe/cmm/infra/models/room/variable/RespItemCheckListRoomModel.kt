package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.RespItemCheckList
import br.com.usinasantafe.cmm.utils.OptionRespCheckList
import br.com.usinasantafe.cmm.utils.TB_RESP_ITEM_CHECK_LIST

@Entity(tableName = TB_RESP_ITEM_CHECK_LIST)
data class RespItemCheckListRoomModel(
    @PrimaryKey
    var id: Int? = null,
    var idHeader: Int,
    val idItem: Int,
    val option: OptionRespCheckList,
    var idServ: Int? = null
)

fun RespItemCheckList.entityToRoomModel(
    idHeader: Int
): RespItemCheckListRoomModel {
    return with(this) {
        RespItemCheckListRoomModel(
            id = id,
            idHeader = idHeader,
            idItem = idItem,
            option = option
        )
    }
}
