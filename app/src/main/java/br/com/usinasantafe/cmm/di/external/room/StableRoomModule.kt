package br.com.usinasantafe.cmm.di.external.room

import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRoomModule {

    @Provides
    @Singleton
    fun provideActivityDao(database: DatabaseRoom): ActivityDao {
        return database.atividadeDao()
    }

    @Provides
    @Singleton
    fun provideBocalDao(database: DatabaseRoom): BocalDao {
        return database.bocalDao()
    }

    @Provides
    @Singleton
    fun provideColabDao(database: DatabaseRoom): ColabDao {
        return database.colabDao()
    }

    @Provides
    @Singleton
    fun provideComponenteDao(database: DatabaseRoom): ComponenteDao {
        return database.componenteDao()
    }

    @Provides
    @Singleton
    fun provideEquipDao(database: DatabaseRoom): EquipDao {
        return database.equipDao()
    }

    @Provides
    @Singleton
    fun provideFrenteDao(database: DatabaseRoom): FrenteDao {
        return database.frenteDao()
    }

    @Provides
    @Singleton
    fun provideItemCheckListDao(database: DatabaseRoom): ItemCheckListDao {
        return database.itemCheckListDao()
    }

    @Provides
    @Singleton
    fun provideItemOSMecanDao(database: DatabaseRoom): ItemOSMecanDao {
        return database.itemOSMecanDao()
    }

    @Provides
    @Singleton
    fun provideLeiraDao(database: DatabaseRoom): LeiraDao {
        return database.leiraDao()
    }

    @Provides
    @Singleton
    fun provideMotoMecDao(database: DatabaseRoom): MotoMecDao {
        return database.motoMecDao()
    }

    @Provides
    @Singleton
    fun provideOSDao(database: DatabaseRoom): OSDao {
        return database.osDao()
    }

    @Provides
    @Singleton
    fun provideParadaDao(database: DatabaseRoom): ParadaDao {
        return database.paradaDao()
    }

    @Provides
    @Singleton
    fun providePressaoBocalDao(database: DatabaseRoom): PressaoBocalDao {
        return database.pressaoBocalDao()
    }

    @Provides
    @Singleton
    fun providePropriedadeDao(database: DatabaseRoom): PropriedadeDao {
        return database.propriedadeDao()
    }

    @Provides
    @Singleton
    fun provideRAtivParadaDao(database: DatabaseRoom): RAtivParadaDao {
        return database.rAtivParadaDao()
    }

    @Provides
    @Singleton
    fun provideREquipActivityDao(database: DatabaseRoom): REquipActivityDao {
        return database.rEquipActivityDao()
    }

    @Provides
    @Singleton
    fun provideREquipPneuDao(database: DatabaseRoom): REquipPneuDao {
        return database.rEquipPneuDao()
    }

    @Provides
    @Singleton
    fun provideRFuncaoAtivParadaDao(database: DatabaseRoom): RFuncaoAtivParadaDao {
        return database.rFuncaoAtivParadaDao()
    }

    @Provides
    @Singleton
    fun provideROSAtivDao(database: DatabaseRoom): ROSActivityDao {
        return database.rOSActivityDao()
    }

    @Provides
    @Singleton
    fun provideServicoDao(database: DatabaseRoom): ServicoDao {
        return database.servicoDao()
    }

    @Provides
    @Singleton
    fun provideTurnDao(database: DatabaseRoom): TurnDao {
        return database.turnoDao()
    }

}