package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMecan // Import da entidade de domínio correta
import br.com.usinasantafe.cmm.utils.TB_ITEM_OS_MECAN // Import da constante do nome da tabela (verificar nome)

@Entity(tableName = TB_ITEM_OS_MECAN)
data class ItemOSMecanRoomModel (
    @PrimaryKey
    val idItemOS: Int,
    val idOS: Int,
    val seqItemOS: Int,
    val idServItemOS: Int,
    val idCompItemOS: Int
)

fun ItemOSMecanRoomModel.roomModelToEntity(): ItemOSMecan {
    return with(this){
        ItemOSMecan(
            idItemOS = this.idItemOS,
            idOS = this.idOS,
            seqItemOS = this.seqItemOS,
            idServItemOS = this.idServItemOS,
            idCompItemOS = this.idCompItemOS
        )
    }
}

fun ItemOSMecan.entityItemOSMecanToRoomModel(): ItemOSMecanRoomModel {
    return with(this){
        ItemOSMecanRoomModel(
            idItemOS = this.idItemOS,
            idOS = this.idOS,
            seqItemOS = this.seqItemOS,
            idServItemOS = this.idServItemOS,
            idCompItemOS = this.idCompItemOS
        )
    }
}
