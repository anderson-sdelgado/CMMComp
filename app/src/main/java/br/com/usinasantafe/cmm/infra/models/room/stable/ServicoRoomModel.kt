package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Servico // Import da entidade de domínio Servico
import br.com.usinasantafe.cmm.utils.TB_SERVICO // Import da constante do nome da tabela (precisa ser criada)

@Entity(tableName = TB_SERVICO) // Define como entidade do Room e especifica o nome da tabela
data class ServicoRoomModel (
    @PrimaryKey // Define idServico como chave primária (sem autoGenerate, assumindo que vem de fonte externa)
    val idServico: Int,
    val codServico: Int,
    val descrServico: String,
)

// Função de extensão para converter ServicoRoomModel para a entidade de domínio Servico
fun ServicoRoomModel.roomToEntity(): Servico {
    return with(this){
        Servico(
            idServico = this.idServico,
            codServico = this.codServico,
            descrServico = this.descrServico
        )
    }
}

// Função de extensão para converter a entidade de domínio Servico para ServicoRoomModel
fun Servico.entityToRoomModel(): ServicoRoomModel {
    return with(this){
        ServicoRoomModel(
            idServico = this.idServico,
            codServico = this.codServico,
            descrServico = this.descrServico
        )
    }
}
