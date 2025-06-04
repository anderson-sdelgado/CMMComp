package br.com.usinasantafe.cmm.external.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.usinasantafe.cmm.external.room.dao.stable.*
import br.com.usinasantafe.cmm.external.room.dao.variable.*
import br.com.usinasantafe.cmm.infra.models.room.stable.*
import br.com.usinasantafe.cmm.infra.models.room.variable.*
import br.com.usinasantafe.cmm.utils.VERSION_DB
import java.util.Date

@Database(
    entities = [
        ActivityRoomModel::class,
        BocalRoomModel::class,
        ColabRoomModel::class,
        ComponenteRoomModel::class,
        EquipRoomModel::class,
        FrenteRoomModel::class,
        ItemCheckListRoomModel::class,
        ItemOSMecanRoomModel::class,
        LeiraRoomModel::class,
        MotoMecRoomModel::class,
        OSRoomModel::class,
        StopRoomModel::class,
        PressaoBocalRoomModel::class,
        PropriedadeRoomModel::class,
        RActivityStopRoomModel::class,
        REquipActivityRoomModel::class,
        REquipPneuRoomModel::class,
        RFuncaoAtivParadaRoomModel::class,
        ROSActivityRoomModel::class,
        ServicoRoomModel::class,
        TurnRoomModel::class,
        HeaderMotoMecRoomModel::class,
        NoteMotoMecRoomModel::class
    ],
    version = VERSION_DB, exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun atividadeDao(): ActivityDao
    abstract fun bocalDao(): BocalDao
    abstract fun colabDao(): ColabDao
    abstract fun componenteDao(): ComponenteDao
    abstract fun equipDao(): EquipDao
    abstract fun frenteDao(): FrenteDao
    abstract fun itemCheckListDao(): ItemCheckListDao
    abstract fun itemOSMecanDao(): ItemOSMecanDao
    abstract fun leiraDao(): LeiraDao
    abstract fun motoMecDao(): MotoMecDao
    abstract fun osDao(): OSDao
    abstract fun paradaDao(): StopDao
    abstract fun pressaoBocalDao(): PressaoBocalDao
    abstract fun propriedadeDao(): PropriedadeDao
    abstract fun rAtivParadaDao(): RActivityStopDao
    abstract fun rEquipActivityDao(): REquipActivityDao
    abstract fun rEquipPneuDao(): REquipPneuDao
    abstract fun rFuncaoAtivParadaDao(): RFuncaoAtivParadaDao
    abstract fun rOSActivityDao(): ROSActivityDao
    abstract fun servicoDao(): ServicoDao
    abstract fun turnoDao(): TurnDao
    abstract fun headerMotoMecDao(): HeaderMotoMecDao
    abstract fun noteMotoMecDao(): NoteMotoMecDao
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
