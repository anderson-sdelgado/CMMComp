package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.lib.TB_FUNCTION_STOP
import br.com.usinasantafe.cmm.lib.TypeStop

@Entity(tableName = TB_FUNCTION_STOP)
data class FunctionStopRoomModel(
    @PrimaryKey
    val idFunctionStop: Int,
    val idStop: Int,
    val typeStop: TypeStop
)

fun FunctionStopRoomModel.roomModelToEntity(): FunctionStop {
    return with(this){
        FunctionStop(
            idFunctionStop = this.idFunctionStop,
            idStop = this.idStop,
            typeStop = this.typeStop
        )
    }
}

fun FunctionStop.entityFunctionStopToRoomModel(): FunctionStopRoomModel {
    return with(this){
        FunctionStopRoomModel(
            idFunctionStop = this.idFunctionStop,
            idStop = this.idStop,
            typeStop = this.typeStop
        )
    }
}