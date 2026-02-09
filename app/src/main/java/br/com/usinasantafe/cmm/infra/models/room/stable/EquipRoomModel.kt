package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.lib.TB_EQUIP
import br.com.usinasantafe.cmm.lib.TypeEquip

@Entity(tableName = TB_EQUIP)
data class EquipRoomModel (
    @PrimaryKey
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val typeEquip: TypeEquip
)

fun EquipRoomModel.roomModelToEntity(): Equip {
    return with(this){
        Equip(
            id = this.id,
            nro = this.nro,
            codClass = this.codClass,
            descrClass = this.descrClass,
            typeEquip = this.typeEquip
        )
    }
}

fun Equip.entityToRoomModel(): EquipRoomModel {
    return with(this){
        EquipRoomModel(
            id = this.id,
            nro = this.nro,
            codClass = this.codClass,
            descrClass = this.descrClass,
            typeEquip = this.typeEquip!!
        )
    }
}
