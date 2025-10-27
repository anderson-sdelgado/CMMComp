package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Servico // Import da entidade de domínio Servico
import br.com.usinasantafe.cmm.utils.TB_SERVICO // Import da constante do nome da tabela (precisa ser criada)

@Entity(tableName = TB_SERVICO)
data class ServicoRoomModel (
    @PrimaryKey
    val idServico: Int,
    val codServico: Int,
    val descrServico: String,
)

fun ServicoRoomModel.roomModelToEntity(): Servico {
    return with(this){
        Servico(
            idServico = this.idServico,
            codServico = this.codServico,
            descrServico = this.descrServico
        )
    }
}

fun Servico.entityServicoToRoomModel(): ServicoRoomModel {
    return with(this){
        ServicoRoomModel(
            idServico = this.idServico,
            codServico = this.codServico,
            descrServico = this.descrServico
        )
    }
}
