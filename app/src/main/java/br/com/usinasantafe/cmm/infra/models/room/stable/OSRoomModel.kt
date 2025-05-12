package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.OS // Import da entidade de domínio OS
import br.com.usinasantafe.cmm.utils.TB_OS // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_OS) // Define como entidade do Room e especifica o nome da tabela
data class OSRoomModel (
    @PrimaryKey // Define idOS como chave primária
    val idOS: Int,
    val nroOS: Int,
    val idLibOS: Int,
    val idProprAgr: Int,
    val areaProgrOS: Double,
    val tipoOS: Int,
    val idEquip: Int
)

// Função de extensão para converter OSRoomModel para a entidade de domínio OS
fun OSRoomModel.roomModelToEntity(): OS {
    return with(this){
        OS(
            idOS = this.idOS,
            nroOS = this.nroOS,
            idLibOS = this.idLibOS,
            idProprAgr = this.idProprAgr,
            areaProgrOS = this.areaProgrOS,
            tipoOS = this.tipoOS,
            idEquip = this.idEquip
        )
    }
}

// Função de extensão para converter a entidade de domínio OS para OSRoomModel
fun OS.entityToRoomModel(): OSRoomModel {
    return with(this){
        OSRoomModel(
            idOS = this.idOS,
            nroOS = this.nroOS,
            idLibOS = this.idLibOS,
            idProprAgr = this.idProprAgr,
            areaProgrOS = this.areaProgrOS,
            tipoOS = this.tipoOS,
            idEquip = this.idEquip
        )
    }
}
