package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.MotoMec // Import da entidade de domínio MotoMec
import br.com.usinasantafe.cmm.utils.TB_MOTOMEC // Import da constante do nome da tabela (suposição)
import br.com.usinasantafe.cmm.utils.TypeOper // Import do enum TypeOper

@Entity(tableName = TB_MOTOMEC)
data class MotoMecRoomModel (
    @PrimaryKey
    val idMotoMec: Int,
    val idOperMotoMec: Int,
    val descrOperMotoMec: String,
    val codFuncaoOperMotoMec: Int,
    val posOperMotoMec: Int,
    val tipoOperMotoMec: Int,
    val aplicOperMotoMec: Int,
    val funcaoOperMotoMec: Int 
)

fun MotoMecRoomModel.roomModelToEntity(): MotoMec {
    return with(this){
        MotoMec(
            idMotoMec = this.idMotoMec,
            idOperMotoMec = this.idOperMotoMec,
            descrOperMotoMec = this.descrOperMotoMec,
            codFuncaoOperMotoMec = this.codFuncaoOperMotoMec,
            posOperMotoMec = this.posOperMotoMec,
            tipoOperMotoMec = this.tipoOperMotoMec,
            aplicOperMotoMec = this.aplicOperMotoMec,
            funcaoOperMotoMec = TypeOper.values()[this.funcaoOperMotoMec]
        )
    }
}

fun MotoMec.entityMotoMecToRoomModel(): MotoMecRoomModel {
    return with(this){
        MotoMecRoomModel(
            idMotoMec = this.idMotoMec,
            idOperMotoMec = this.idOperMotoMec,
            descrOperMotoMec = this.descrOperMotoMec,
            codFuncaoOperMotoMec = this.codFuncaoOperMotoMec,
            posOperMotoMec = this.posOperMotoMec,
            tipoOperMotoMec = this.tipoOperMotoMec,
            aplicOperMotoMec = this.aplicOperMotoMec,
            funcaoOperMotoMec = this.funcaoOperMotoMec.ordinal
        )
    }
}
