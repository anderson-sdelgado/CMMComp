package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Propriedade // Import da entidade de domínio Propriedade
import br.com.usinasantafe.cmm.utils.TB_PROPRIEDADE // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_PROPRIEDADE)
data class PropriedadeRoomModel (
    @PrimaryKey
    val idPropriedade: Int,
    val codPropriedade: Int,
    val descrPropriedade: String
)

fun PropriedadeRoomModel.roomModelToEntity(): Propriedade {
    return with(this){
        Propriedade(
            idPropriedade = this.idPropriedade,
            codPropriedade = this.codPropriedade,
            descrPropriedade = this.descrPropriedade
        )
    }
}

fun Propriedade.entityPropriedadeToRoomModel(): PropriedadeRoomModel {
    return with(this){
        PropriedadeRoomModel(
            idPropriedade = this.idPropriedade,
            codPropriedade = this.codPropriedade,
            descrPropriedade = this.descrPropriedade
        )
    }
}
