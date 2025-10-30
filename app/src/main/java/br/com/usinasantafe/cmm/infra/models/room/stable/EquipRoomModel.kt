package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Equip // Import da entidade de domínio Equip
import br.com.usinasantafe.cmm.utils.TB_EQUIP // Import da constante do nome da tabela
import br.com.usinasantafe.cmm.utils.TypeEquip

@Entity(tableName = TB_EQUIP)
data class EquipRoomModel (
    @PrimaryKey
    val id: Int,
    val nro: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeEquip: TypeEquip,
    var hourMeter: Double,
    val classify: Int,
    val flagMechanic: Boolean,
    val flagTire: Boolean
)

fun EquipRoomModel.roomModelToEntity(): Equip {
    return with(this){
        Equip(
            id = this.id,
            nro = this.nro,
            codClass = this.codClass,
            descrClass = this.descrClass,
            codTurnEquip = this.codTurnEquip,
            idCheckList = this.idCheckList,
            typeEquip = this.typeEquip,
            hourMeter = this.hourMeter,
            classify = this.classify,
            flagMechanic = this.flagMechanic,
            flagTire = this.flagTire
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
            codTurnEquip = this.codTurnEquip,
            idCheckList = this.idCheckList,
            typeEquip = this.typeEquip,
            hourMeter = this.hourMeter,
            classify = this.classify,
            flagMechanic = this.flagMechanic,
            flagTire = this.flagTire
        )
    }
}
