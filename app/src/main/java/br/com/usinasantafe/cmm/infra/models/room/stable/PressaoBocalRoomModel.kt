package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.PressaoBocal // Import da entidade de domínio PressaoBocal
import br.com.usinasantafe.cmm.utils.TB_PRESSAO_BOCAL // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PRESSAO_BOCAL)
data class PressaoBocalRoomModel (
    @PrimaryKey
    val idPressaoBocal: Int,
    val idBocal: Int,
    val valorPressao: Double,
    val valorVeloc: Int
)

fun PressaoBocalRoomModel.roomModelToEntity(): PressaoBocal {
    return with(this){
        PressaoBocal(
            idPressaoBocal = this.idPressaoBocal,
            idBocal = this.idBocal,
            valorPressao = this.valorPressao,
            valorVeloc = this.valorVeloc
        )
    }
}

fun PressaoBocal.entityPressaoBocalToRoomModel(): PressaoBocalRoomModel {
    return with(this){
        PressaoBocalRoomModel(
            idPressaoBocal = this.idPressaoBocal,
            idBocal = this.idBocal,
            valorPressao = this.valorPressao,
            valorVeloc = this.valorVeloc
        )
    }
}
