package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.utils.TB_R_EQUIP_ACTIVITY

@Entity(tableName = TB_R_EQUIP_ACTIVITY)
data class REquipActivityRoomModel (
    @PrimaryKey(autoGenerate = true)
    val idREquipActivity: Int? = null,
    val idEquip: Int,
    val idActivity: Int
)

fun REquipActivityRoomModel.roomModelToEntity(): REquipActivity {
    return with(this){
        REquipActivity(
            idREquipActivity = this.idREquipActivity,
            idEquip = this.idEquip,
            idActivity = this.idActivity
        )
    }
}

fun REquipActivity.entityREquipActivityToRoomModel(): REquipActivityRoomModel {
    return with(this){
        REquipActivityRoomModel(
            idREquipActivity = this.idREquipActivity,
            idEquip = this.idEquip,
            idActivity = this.idActivity
        )
    }
}
