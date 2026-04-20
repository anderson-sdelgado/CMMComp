package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.OS // Import da entidade de domínio OS
import br.com.usinasantafe.cmm.lib.TB_OS // Import da constante do nome da tabela (suposição)

@Entity(tableName = TB_OS)
data class OSRoomModel (
    @PrimaryKey
    val idOS: Int,
    val nroOS: Int,
    val idReleaseOS: Int? = null,
    val idPropAgr: Int? = null,
    val areaOS: Double? = null,
)

fun OSRoomModel.roomModelToEntity(): OS {
    return with(this){
        OS(
            idOS = this.idOS,
            nroOS = this.nroOS,
            idReleaseOS = this.idReleaseOS,
            idPropAgr = this.idPropAgr,
            areaOS = this.areaOS
        )
    }
}

fun OS.entityToRoomModel(): OSRoomModel {
    return with(this){
        OSRoomModel(
            idOS = this.idOS,
            nroOS = this.nroOS,
            idReleaseOS = this.idReleaseOS,
            idPropAgr = this.idPropAgr,
            areaOS = this.areaOS
        )
    }
}
