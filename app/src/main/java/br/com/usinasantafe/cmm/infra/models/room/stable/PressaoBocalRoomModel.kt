package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.PressaoBocal // Import da entidade de domínio PressaoBocal
import br.com.usinasantafe.cmm.utils.TB_PRESSAO_BOCAL // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PRESSAO_BOCAL) // Define como entidade do Room e especifica o nome da tabela
data class PressaoBocalRoomModel (
    @PrimaryKey // Define idPressaoBocal como chave primária
    val idPressaoBocal: Int,
    val idBocal: Int, // Chave estrangeira para Bocal
    val valorPressao: Double,
    val valorVeloc: Int
)

// Função de extensão para converter PressaoBocalRoomModel para a entidade de domínio PressaoBocal
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

// Função de extensão para converter a entidade de domínio PressaoBocal para PressaoBocalRoomModel
fun PressaoBocal.entityToRoomModel(): PressaoBocalRoomModel {
    return with(this){
        PressaoBocalRoomModel(
            idPressaoBocal = this.idPressaoBocal,
            idBocal = this.idBocal,
            valorPressao = this.valorPressao,
            valorVeloc = this.valorVeloc
        )
    }
}
