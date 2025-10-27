package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Componente // Import da entidade de domínio
import br.com.usinasantafe.cmm.utils.TB_COMPONENTE // Import da constante do nome da tabela

@Entity(tableName = TB_COMPONENTE)
data class ComponenteRoomModel (
    @PrimaryKey
    val idComponente: Int,
    val codComponente: Int,
    val descrComponente: String
)

fun ComponenteRoomModel.roomModelToEntity(): Componente {
    return with(this){
        Componente(
            idComponente = this.idComponente,
            codComponente = this.codComponente,
            descrComponente = this.descrComponente,
        )
    }
}

fun Componente.entityComponenteToRoomModel(): ComponenteRoomModel {
    return with(this){
        ComponenteRoomModel(
            idComponente = this.idComponente,
            codComponente = this.codComponente,
            descrComponente = this.descrComponente,
        )
    }
}
