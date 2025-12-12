package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.Equip // Import da entidade de domínio Equip
import br.com.usinasantafe.cmm.lib.TB_EQUIP // Import da constante do nome da tabela
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary

@Entity(tableName = TB_EQUIP)
data class EquipRoomModel (
    @PrimaryKey
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val typeEquip: TypeEquipSecondary
)

fun EquipRoomModel.roomModelToEntity(): Equip {
    return with(this){
        Equip(
            id = this.id,
            nro = this.nro,
            codClass = this.codClass,
            descrClass = this.descrClass,
            typeEquipSecondary = this.typeEquip
        )
    }
}

fun Equip.entityEquipToRoomModel(): EquipRoomModel {
    return with(this){
        EquipRoomModel(
            id = this.id,
            nro = this.nro,
            codClass = this.codClass,
            descrClass = this.descrClass,
            typeEquip = this.typeEquipSecondary!!
        )
    }
}
