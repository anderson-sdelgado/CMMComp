package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList // Import da entidade de domínio ItemCheckList
import br.com.usinasantafe.cmm.utils.TB_ITEM_CHECKLIST // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_ITEM_CHECKLIST) // Define como entidade do Room e especifica o nome da tabela
data class ItemCheckListRoomModel (
    @PrimaryKey // Define idItemCheckList como chave primária
    val idItemCheckList: Int, // ID do Item do Checklist (usei Int como padrão)
    val idCheckList: Int, // ID do Checklist ao qual este item pertence
    val descrItemCheckList: String // Descrição do item do checklist
)

// Função de extensão para converter ItemCheckListRoomModel para a entidade de domínio ItemCheckList
fun ItemCheckListRoomModel.roomModelToEntity(): ItemCheckList {
    return with(this){
        ItemCheckList(
            idItemCheckList = this.idItemCheckList,
            idCheckList = this.idCheckList,
            descrItemCheckList = this.descrItemCheckList
        )
    }
}

// Função de extensão para converter a entidade de domínio ItemCheckList para ItemCheckListRoomModel
fun ItemCheckList.entityToRoomModel(): ItemCheckListRoomModel {
    return with(this){
        ItemCheckListRoomModel(
            idItemCheckList = this.idItemCheckList,
            idCheckList = this.idCheckList,
            descrItemCheckList = this.descrItemCheckList
        )
    }
}
