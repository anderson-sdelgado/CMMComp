package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Colab
import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.utils.TB_FUNCTION_ACTIVITY
import br.com.usinasantafe.cmm.utils.TypeActivity

@Entity(tableName = TB_FUNCTION_ACTIVITY)
data class FunctionActivityRoomModel(
    @PrimaryKey
    val idRFunctionActivity: Int,
    val idActivity: Int,
    val typeActivity: TypeActivity,
)

fun FunctionActivityRoomModel.roomModelToEntity(): FunctionActivity {
    return with(this){
        FunctionActivity(
            idRFunctionActivity = this.idRFunctionActivity,
            idActivity = this.idActivity,
            typeActivity = this.typeActivity
        )
    }
}

fun FunctionActivity.entityFunctionActivityToRoomModel(): FunctionActivityRoomModel {
    return with(this){
        FunctionActivityRoomModel(
            idRFunctionActivity = this.idRFunctionActivity,
            idActivity = this.idActivity,
            typeActivity = this.typeActivity
        )
    }
}