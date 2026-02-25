package br.com.usinasantafe.cmm.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.stable.Component
import br.com.usinasantafe.cmm.lib.TB_COMPONENT

@Entity(tableName = TB_COMPONENT)
data class ComponentRoomModel(
    @PrimaryKey
    val id: Int,
    val cod: Int,
    val descr: String
)

fun ComponentRoomModel.roomModelToEntity(): Component {
    return with(this) {
        Component(
            id = this.id,
            cod = this.cod,
            descr = this.descr,
        )
    }
}

fun Component.entityToRoomModel(): ComponentRoomModel {
    return with(this) {
        ComponentRoomModel(
            id = this.id,
            cod = this.cod,
            descr = this.descr,
        )
    }
}
