package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Componente // Import da entidade de domínio
import br.com.usinasantafe.cmm.utils.TB_COMPONENTE // Import da constante do nome da tabela

@Entity(tableName = TB_COMPONENTE) // Define como entidade do Room e especifica o nome da tabela
data class ComponenteRoomModel (
    @PrimaryKey // Define idComponente como chave primária
    val idComponente: Int, // ID do Componente (ajustado para Int)
    val codComponente: Int, // Código do Componente (campo adicionado)
    val descrComponente: String // Descrição do Componente
)

// Função de extensão para converter ComponenteRoomModel para a entidade de domínio Componente
fun ComponenteRoomModel.roomToEntity(): Componente {
    return with(this){
        Componente(
            idComponente = this.idComponente,
            codComponente = this.codComponente, // Mapeamento adicionado
            descrComponente = this.descrComponente,
        )
    }
}

// Função de extensão para converter a entidade de domínio Componente para ComponenteRoomModel
fun Componente.entityToRoomModel(): ComponenteRoomModel {
    return with(this){
        ComponenteRoomModel(
            idComponente = this.idComponente,
            codComponente = this.codComponente, // Mapeamento adicionado
            descrComponente = this.descrComponente,
        )
    }
}
