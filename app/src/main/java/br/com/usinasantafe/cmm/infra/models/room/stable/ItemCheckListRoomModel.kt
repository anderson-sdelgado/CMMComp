package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList // Import da entidade de domínio ItemCheckList
import br.com.usinasantafe.cmm.lib.TB_ITEM_CHECK_LIST // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_ITEM_CHECK_LIST)
data class ItemCheckListRoomModel (
    @PrimaryKey
    val idItemCheckList: Int,
    val idCheckList: Int,
    val descrItemCheckList: String
)

fun ItemCheckListRoomModel.roomModelToEntity(): ItemCheckList {
    return with(this){
        ItemCheckList(
            idItemCheckList = this.idItemCheckList,
            idCheckList = this.idCheckList,
            descrItemCheckList = this.descrItemCheckList
        )
    }
}

fun ItemCheckList.entityToRoomModel(): ItemCheckListRoomModel {
    return with(this){
        ItemCheckListRoomModel(
            idItemCheckList = this.idItemCheckList,
            idCheckList = this.idCheckList,
            descrItemCheckList = this.descrItemCheckList
        )
    }
}
