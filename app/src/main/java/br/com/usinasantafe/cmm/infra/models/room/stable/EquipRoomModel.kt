package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Equip // Import da entidade de domínio Equip
import br.com.usinasantafe.cmm.utils.TB_EQUIP // Import da constante do nome da tabela

@Entity(tableName = TB_EQUIP)
data class EquipRoomModel (
    @PrimaryKey
    val idEquip: Int,
    val nroEquip: Long,
    val codClass: Int,
    val descrClass: String,
    val codTurnEquip: Int,
    val idCheckList: Int,
    val typeFert: Int,
    var hourMeter: Double,
    val classify: Int,
    val flagMechanic: Boolean
)

fun EquipRoomModel.roomModelToEntity(): Equip {
    return with(this){
        Equip(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            codClass = this.codClass,
            descrClass = this.descrClass,
            codTurnEquip = this.codTurnEquip,
            idCheckList = this.idCheckList,
            typeFert = this.typeFert,
            hourMeter = this.hourMeter,
            classify = this.classify,
            flagMechanic = this.flagMechanic
        )
    }
}

fun Equip.entityEquipToRoomModel(): EquipRoomModel {
    return with(this){
        EquipRoomModel(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            codClass = this.codClass,
            descrClass = this.descrClass,
            codTurnEquip = this.codTurnEquip,
            idCheckList = this.idCheckList,
            typeFert = this.typeFert,
            hourMeter = this.hourMeter,
            classify = this.classify,
            flagMechanic = this.flagMechanic
        )
    }
}
