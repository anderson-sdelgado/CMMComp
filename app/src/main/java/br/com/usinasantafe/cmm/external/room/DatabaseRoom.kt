package br.com.usinasantafe.cmm.external.room

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.usinasantafe.cmm.external.room.dao.*
import br.com.usinasantafe.cmm.infra.models.room.stable.*
import br.com.usinasantafe.cmm.utils.VERSION_DB

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
        ParadaRoomModel::class,
        PressaoBocalRoomModel::class,
        PropriedadeRoomModel::class,
        RAtivParadaRoomModel::class,
        REquipAtivRoomModel::class,
        REquipPneuRoomModel::class,
        RFuncaoAtivParadaRoomModel::class,
        ROSActivityRoomModel::class,
        ServicoRoomModel::class,
        TurnRoomModel::class,
    ],
    version = VERSION_DB, exportSchema = true,
)
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
    abstract fun paradaDao(): ParadaDao
    abstract fun pressaoBocalDao(): PressaoBocalDao
    abstract fun propriedadeDao(): PropriedadeDao
    abstract fun rAtivParadaDao(): RAtivParadaDao
    abstract fun rEquipAtivDao(): REquipAtivDao
    abstract fun rEquipPneuDao(): REquipPneuDao
    abstract fun rFuncaoAtivParadaDao(): RFuncaoAtivParadaDao
    abstract fun rOSActivityDao(): ROSActivityDao
    abstract fun servicoDao(): ServicoDao
    abstract fun turnoDao(): TurnDao
}

