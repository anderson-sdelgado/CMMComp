package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.REquipAtiv
import br.com.usinasantafe.cmm.utils.TB_R_EQUIP_ATIV

@Entity(tableName = TB_R_EQUIP_ATIV)
data class REquipAtivRoomModel (
    @PrimaryKey(autoGenerate = true)
    val idREquipAtiv: Int,
    val idEquip: Int,
    val idAtiv: Int
)

fun REquipAtivRoomModel.roomModelToEntity(): REquipAtiv {
    return with(this){
        REquipAtiv(
            idREquipAtiv = this.idREquipAtiv,
            idEquip = this.idEquip,
            idAtiv = this.idAtiv
        )
    }
}

fun REquipAtiv.entityToRoomModel(): REquipAtivRoomModel {
    return with(this){
        REquipAtivRoomModel(
            idREquipAtiv = this.idREquipAtiv,
            idEquip = this.idEquip,
            idAtiv = this.idAtiv
        )
    }
}
