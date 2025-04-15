package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Bocal // Supondo que a entidade de domínio Bocal exista neste pacote
import br.com.usinasantafe.cmm.utils.TB_BOCAL // Supondo que a constante para o nome da tabela exista

@Entity(tableName = TB_BOCAL) // Define como entidade do Room e especifica o nome da tabela
data class BocalRoomModel (
    @PrimaryKey // Define idBocal como chave primária
    val idBocal: Long, // ID do Bocal (usei Long como em ColabRoomModel, mas pode ser Int se preferir)
    val descrBocal: String // Descrição do Bocal
)

// Função de extensão para converter BocalRoomModel para a entidade de domínio Bocal
fun BocalRoomModel.roomToEntity(): Bocal {
    return with(this){
        Bocal(
            idBocal = this.idBocal,
            descrBocal = this.descrBocal,
        )
    }
}

// Função de extensão para converter a entidade de domínio Bocal para BocalRoomModel
fun Bocal.entityToRoomModel(): BocalRoomModel {
    return with(this){
        BocalRoomModel(
            idBocal = this.idBocal,
            descrBocal = this.descrBocal,
        )
    }
}
