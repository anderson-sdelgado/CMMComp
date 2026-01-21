package br.com.usinasantafe.cmm.lib

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.usinasantafe.cmm.external.room.dao.stable.*
import br.com.usinasantafe.cmm.external.room.dao.variable.*
import br.com.usinasantafe.cmm.infra.models.room.stable.*
import br.com.usinasantafe.cmm.infra.models.room.variable.*
import java.util.Date

@Database(
    entities = [
        ActivityRoomModel::class,
        ColabRoomModel::class,
        EquipRoomModel::class,
        FunctionActivityRoomModel::class,
        FunctionStopRoomModel::class,
        ItemCheckListRoomModel::class,
        ItemMenuRoomModel::class,
        OSRoomModel::class,
        StopRoomModel::class,
        RActivityStopRoomModel::class,
        REquipActivityRoomModel::class,
        ROSActivityRoomModel::class,
        RItemMenuStopRoomModel::class,
        TurnRoomModel::class,
        HeaderMotoMecRoomModel::class,
        ItemMotoMecRoomModel::class,
        HeaderCheckListRoomModel::class,
        ItemRespCheckListRoomModel::class,
        MechanicRoomModel::class,
        InputCompostingRoomModel::class,
        CompoundCompostingRoomModel::class,
        PerformanceRoomModel::class
    ],
    version = VERSION_DB, exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun colabDao(): ColabDao
    abstract fun equipDao(): EquipDao
    abstract fun functionActivityDao(): FunctionActivityDao
    abstract fun functionStopDao(): FunctionStopDao
    abstract fun itemCheckListDao(): ItemCheckListDao
    abstract fun itemMenuDao(): ItemMenuDao
    abstract fun osDao(): OSDao
    abstract fun rActivityStopDao(): RActivityStopDao
    abstract fun rEquipActivityDao(): REquipActivityDao
    abstract fun rOSActivityDao(): ROSActivityDao
    abstract fun rItemMenuStopDao(): RItemMenuStopDao
    abstract fun stopDao(): StopDao
    abstract fun turnDao(): TurnDao
    abstract fun headerMotoMecDao(): HeaderMotoMecDao
    abstract fun noteMotoMecDao(): ItemMotoMecDao
    abstract fun headerCheckListDao(): HeaderCheckListDao
    abstract fun respItemCheckListDao(): ItemRespCheckListDao
    abstract fun noteMechanicDao(): MechanicDao
    abstract fun inputCompostingDao(): InputCompostingDao
    abstract fun compoundCompostingDao(): CompoundCompostingDao
    abstract fun performanceDao(): PerformanceDao
}

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}
