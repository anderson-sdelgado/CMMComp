package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Frente // Import da entidade de domínio Frente
import br.com.usinasantafe.cmm.utils.TB_FRENTE // Import da constante do nome da tabela

@Entity(tableName = TB_FRENTE)
data class FrenteRoomModel (
    @PrimaryKey
    val idFrente: Int,
    val codFrente: Int,
    val descrFrente: String
)

fun FrenteRoomModel.roomModelToEntity(): Frente {
    return with(this){
        Frente(
            idFrente = this.idFrente,
            codFrente = this.codFrente,
            descrFrente = this.descrFrente
        )
    }
}

fun Frente.entityFrenteToRoomModel(): FrenteRoomModel {
    return with(this){
        FrenteRoomModel(
            idFrente = this.idFrente,
            codFrente = this.codFrente,
            descrFrente = this.descrFrente
        )
    }
}
