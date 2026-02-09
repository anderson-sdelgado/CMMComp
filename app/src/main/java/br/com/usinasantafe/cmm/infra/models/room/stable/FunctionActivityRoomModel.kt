package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.lib.TB_FUNCTION_ACTIVITY
import br.com.usinasantafe.cmm.lib.TypeActivity

@Entity(tableName = TB_FUNCTION_ACTIVITY)
data class FunctionActivityRoomModel(
    @PrimaryKey
    val idFunctionActivity: Int,
    val idActivity: Int,
    val typeActivity: TypeActivity,
)

fun FunctionActivityRoomModel.roomModelToEntity(): FunctionActivity {
    return with(this){
        FunctionActivity(
            idFunctionActivity = this.idFunctionActivity,
            idActivity = this.idActivity,
            typeActivity = this.typeActivity
        )
    }
}

fun FunctionActivity.entityToRoomModel(): FunctionActivityRoomModel {
    return with(this){
        FunctionActivityRoomModel(
            idFunctionActivity = this.idFunctionActivity,
            idActivity = this.idActivity,
            typeActivity = this.typeActivity
        )
    }
}