package br.com.usinasantafe.cmm.external.room

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.utils.VERSION_DB

@Database(
    entities = [
        ColabRoomModel::class,
    ],
    version = VERSION_DB, exportSchema = true,
)
abstract class DatabaseRoom : RoomDatabase() {
}

