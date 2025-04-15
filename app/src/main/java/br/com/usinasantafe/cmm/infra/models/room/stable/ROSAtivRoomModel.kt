package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ROSAtiv 
import br.com.usinasantafe.cmm.utils.TB_R_OS_ATIV 

@Entity(tableName = TB_R_OS_ATIV) 
data class ROSAtivRoomModel (
    @PrimaryKey(autoGenerate = true) // Define idROSAtiv como chave primária autogerada
    val idROSAtiv: Int,
    val idOS: Int, 
    val idAtiv: Int 
)

fun ROSAtivRoomModel.roomToEntity(): ROSAtiv {
    return with(this){
        ROSAtiv(
            idROSAtiv = this.idROSAtiv,
            idOS = this.idOS,
            idAtiv = this.idAtiv
        )
    }
}

fun ROSAtiv.entityToRoomModel(): ROSAtivRoomModel {
    return with(this){
        ROSAtivRoomModel(
            idROSAtiv = this.idROSAtiv,
            idOS = this.idOS,
            idAtiv = this.idAtiv
        )
    }
}
