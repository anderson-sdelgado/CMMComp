package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.OS // Import da entidade de domínio OS
import br.com.usinasantafe.cmm.lib.TB_OS // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_OS)
data class OSRoomModel (
    @PrimaryKey
    val id: Int,
    val nro: Int,
    val idRelease: Int? = null,
    val idPropAgr: Int? = null,
    val area: Double? = null,
)

fun OSRoomModel.roomModelToEntity(): OS {
    return with(this){
        OS(
            id = this.id,
            nro = this.nro,
            idRelease = this.idRelease,
            idPropAgr = this.idPropAgr,
            area = this.area
        )
    }
}

fun OS.entityToRoomModel(): OSRoomModel {
    return with(this){
        OSRoomModel(
            id = this.id,
            nro = this.nro,
            idRelease = this.idRelease,
            idPropAgr = this.idPropAgr,
            area = this.area
        )
    }
}
