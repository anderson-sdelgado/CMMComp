package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Bocal // Supondo que a entidade de domínio Bocal exista neste pacote
import br.com.usinasantafe.cmm.utils.TB_BOCAL // Supondo que a constante para o nome da tabela exista

@Entity(tableName = TB_BOCAL)
data class BocalRoomModel (
    @PrimaryKey
    val idBocal: Int,
    val codBocal: Int,
    val descrBocal: String,
)

fun BocalRoomModel.roomToEntity(): Bocal {
    return with(this){
        Bocal(
            idBocal = this.idBocal,
            codBocal = this.codBocal,
            descrBocal = this.descrBocal,
        )
    }
}

// Função de extensão para converter a entidade de domínio Bocal para BocalRoomModel
fun Bocal.entityToRoomModel(): BocalRoomModel {
    return with(this){
        BocalRoomModel(
            idBocal = this.idBocal,
            codBocal = this.codBocal,
            descrBocal = this.descrBocal,
        )
    }
}
