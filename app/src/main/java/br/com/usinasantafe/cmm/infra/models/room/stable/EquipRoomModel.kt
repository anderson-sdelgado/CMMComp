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
    val hourmeter: Double,
    val measurement: Double,
    val type: Int,
    val classify: Int,
    val flagApontMecan: Boolean,
    val flagApontPneu: Boolean
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
            hourmeter = this.hourmeter,
            measurement = this.measurement,
            type = this.type,
            classify = this.classify,
            flagApontMecan = this.flagApontMecan,
            flagApontPneu = this.flagApontPneu
        )
    }
}

fun Equip.entityToRoomModel(): EquipRoomModel {
    return with(this){
        EquipRoomModel(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            codClass = this.codClass,
            descrClass = this.descrClass,
            codTurnEquip = this.codTurnEquip,
            idCheckList = this.idCheckList,
            typeFert = this.typeFert,
            hourmeter = this.hourmeter,
            measurement = this.measurement,
            type = this.type,
            classify = this.classify,
            flagApontMecan = this.flagApontMecan,
            flagApontPneu = this.flagApontPneu
        )
    }
}
