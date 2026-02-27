package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic
import br.com.usinasantafe.cmm.lib.TB_ITEM_OS_MECHANIC

@Entity(tableName = TB_ITEM_OS_MECHANIC)
data class ItemOSMechanicRoomModel(
    @PrimaryKey
    val id: Int,
    val nroOS: Int,
    val seqItem: Int,
    val idServ: Int,
    val idComp: Int
)

fun ItemOSMechanicRoomModel.roomModelToEntity(): ItemOSMechanic {
    return with(this){
        ItemOSMechanic(
            id = this.id,
            nroOS = this.nroOS,
            seqItem = this.seqItem,
            idServ = this.idServ,
            idComp = this.idComp
        )
    }
}

fun ItemOSMechanic.entityToRoomModel(): ItemOSMechanicRoomModel {
    return with(this) {
        ItemOSMechanicRoomModel(
            id = this.id,
            nroOS = this.nroOS,
            seqItem = this.seqItem,
            idServ = this.idServ,
            idComp = this.idComp
        )
    }
}
