package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.ItemRespCheckList
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.lib.TB_ITEM_RESP_CHECK_LIST

@Entity(tableName = TB_ITEM_RESP_CHECK_LIST)
data class ItemRespCheckListRoomModel(
    @PrimaryKey
    var id: Int? = null,
    var idHeader: Int,
    val idItem: Int,
    val option: OptionRespCheckList,
    var idServ: Int? = null
)

fun ItemRespCheckList.entityToRoomModel(
    idHeader: Int
): ItemRespCheckListRoomModel {
    return with(this) {
        ItemRespCheckListRoomModel(
            id = id,
            idHeader = idHeader,
            idItem = idItem,
            option = option
        )
    }
}
