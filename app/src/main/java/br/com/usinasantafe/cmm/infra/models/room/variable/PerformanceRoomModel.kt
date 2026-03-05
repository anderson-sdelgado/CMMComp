package br.com.usinasantafe.cmm.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.cmm.domain.entities.variable.Performance
import br.com.usinasantafe.cmm.lib.TB_PERFORMANCE
import br.com.usinasantafe.cmm.utils.required
import java.util.Date

@Entity(tableName = TB_PERFORMANCE)
data class PerformanceRoomModel(
    @PrimaryKey
    var id: Int? = null,
    val idHeader: Int,
    val nroOS: Int,
    var value: Double? = null,
    val dateHour: Date = Date()
)

fun PerformanceRoomModel.roomModelToEntity(): Performance {
    return with(this) {
        Performance(
            id = ::id.required(),
            idHeader = idHeader,
            nroOS = nroOS,
            value = value,
            dateHour = dateHour
        )
    }
}