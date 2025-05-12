package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.REquipPneu
import br.com.usinasantafe.cmm.utils.TB_R_EQUIP_PNEU

@Entity(tableName = TB_R_EQUIP_PNEU)
data class REquipPneuRoomModel (
    @PrimaryKey(autoGenerate = true)
    val idREquipPneu: Int,
    val idEquip: Int,
    val idPosConfPneu: Int,
    val posPneu: Int,
    val statusPneu: Int,
)

fun REquipPneuRoomModel.roomModelToEntity(): REquipPneu {
    return REquipPneu(
        idREquipPneu = this.idREquipPneu,
        idEquip = this.idEquip,
        idPosConfPneu = this.idPosConfPneu,
        posPneu = this.posPneu,
        statusPneu = this.statusPneu
    )
}

fun REquipPneu.entityToRoomModel(): REquipPneuRoomModel {
    return REquipPneuRoomModel(
        idREquipPneu = this.idREquipPneu,
        idEquip = this.idEquip,
        idPosConfPneu = this.idPosConfPneu,
        posPneu = this.posPneu,
        statusPneu = this.statusPneu
    )
}
