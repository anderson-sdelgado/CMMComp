package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMecan // Import da entidade de domínio correta
import br.com.usinasantafe.cmm.utils.TB_ITEM_OS_MECAN // Import da constante do nome da tabela (verificar nome)

@Entity(tableName = TB_ITEM_OS_MECAN) // Define como entidade do Room e especifica o nome da tabela
data class ItemOSMecanRoomModel (
    @PrimaryKey // Define idItemOS como chave primária
    val idItemOS: Int,      // ID único do Item da OS
    val idOS: Int,          // ID da Ordem de Serviço (OS) a qual pertence
    val seqItemOS: Int,     // Sequência do item dentro da OS
    val idServItemOS: Int,  // ID do Serviço associado ao item
    val idCompItemOS: Int   // ID do Componente associado ao item
)

// Função de extensão para converter ItemOSMecanRoomModel para a entidade de domínio ItemOSMecan
fun ItemOSMecanRoomModel.roomModelToEntity(): ItemOSMecan {
    return with(this){
        ItemOSMecan(
            idItemOS = this.idItemOS,
            idOS = this.idOS,
            seqItemOS = this.seqItemOS,
            idServItemOS = this.idServItemOS,
            idCompItemOS = this.idCompItemOS
        )
    }
}

// Função de extensão para converter a entidade de domínio ItemOSMecan para ItemOSMecanRoomModel
fun ItemOSMecan.entityToRoomModel(): ItemOSMecanRoomModel {
    return with(this){
        ItemOSMecanRoomModel(
            idItemOS = this.idItemOS,
            idOS = this.idOS,
            seqItemOS = this.seqItemOS,
            idServItemOS = this.idServItemOS,
            idCompItemOS = this.idCompItemOS
        )
    }
}
