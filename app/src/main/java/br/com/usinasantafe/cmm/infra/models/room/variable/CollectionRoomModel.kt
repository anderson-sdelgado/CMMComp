package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.Collection
import br.com.usinasantafe.cmm.lib.TB_COLLECTION
import java.util.Date

@Entity(tableName = TB_COLLECTION)
data class CollectionRoomModel(
    @PrimaryKey
    var id: Int? = null,
    val idHeader: Int,
    val nroOS: Int,
    var value: Double? = null,
    val dateHour: Date = Date()
)

fun CollectionRoomModel.roomModelToEntity(): Collection {
    return with(this) {
        Collection(
            id = this.id!!,
            idHeader = this.idHeader,
            nroOS = this.nroOS,
            value = this.value,
            dateHour = this.dateHour
        )
    }
}
