package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Propriedade // Import da entidade de domínio Propriedade
import br.com.usinasantafe.cmm.utils.TB_PROPRIEDADE // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PROPRIEDADE) // Define como entidade do Room e especifica o nome da tabela
data class PropriedadeRoomModel (
    @PrimaryKey // Define idPropriedade como chave primária
    val idPropriedade: Int,
    val codPropriedade: Int,
    val descrPropriedade: String
)

// Função de extensão para converter PropriedadeRoomModel para a entidade de domínio Propriedade
fun PropriedadeRoomModel.roomModelToEntity(): Propriedade {
    return with(this){
        Propriedade(
            idPropriedade = this.idPropriedade,
            codPropriedade = this.codPropriedade,
            descrPropriedade = this.descrPropriedade
        )
    }
}

// Função de extensão para converter a entidade de domínio Propriedade para PropriedadeRoomModel
fun Propriedade.entityToRoomModel(): PropriedadeRoomModel {
    return with(this){
        PropriedadeRoomModel(
            idPropriedade = this.idPropriedade,
            codPropriedade = this.codPropriedade,
            descrPropriedade = this.descrPropriedade
        )
    }
}
