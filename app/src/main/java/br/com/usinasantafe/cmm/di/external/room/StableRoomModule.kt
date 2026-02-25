package br.com.usinasantafe.cmm.di.external.room

import br.com.usinasantafe.cmm.lib.DatabaseRoom
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
        return database.activityDao()
    }

    @Provides
    @Singleton
    fun provideColabDao(database: DatabaseRoom): ColabDao {
        return database.colabDao()
    }

    @Provides
    @Singleton
    fun provideEquipDao(database: DatabaseRoom): EquipDao {
        return database.equipDao()
    }

    @Provides
    @Singleton
    fun provideItemCheckListDao(database: DatabaseRoom): ItemCheckListDao {
        return database.itemCheckListDao()
    }

    @Provides
    @Singleton
    fun provideOSDao(database: DatabaseRoom): OSDao {
        return database.osDao()
    }

    @Provides
    @Singleton
    fun provideStopDao(database: DatabaseRoom): StopDao {
        return database.stopDao()
    }

    @Provides
    @Singleton
    fun provideRActivityStopDao(database: DatabaseRoom): RActivityStopDao {
        return database.rActivityStopDao()
    }

    @Provides
    @Singleton
    fun provideREquipActivityDao(database: DatabaseRoom): REquipActivityDao {
        return database.rEquipActivityDao()
    }

    @Provides
    @Singleton
    fun provideROSActivityDao(database: DatabaseRoom): ROSActivityDao {
        return database.rOSActivityDao()
    }

    @Provides
    @Singleton
    fun provideRItemMenuStopDao(database: DatabaseRoom): RItemMenuStopDao {
        return database.rItemMenuStopDao()
    }
    @Provides
    @Singleton
    fun provideTurnDao(database: DatabaseRoom): TurnDao {
        return database.turnDao()
    }

    @Provides
    @Singleton
    fun provideFunctionActivityDao(database: DatabaseRoom): FunctionActivityDao {
        return database.functionActivityDao()
    }

    @Provides
    @Singleton
    fun provideFunctionStopDao(database: DatabaseRoom): FunctionStopDao {
        return database.functionStopDao()
    }

    @Provides
    @Singleton
    fun provideItemMenuDao(database: DatabaseRoom): ItemMenuDao {
        return database.itemMenuDao()
    }

    @Provides
    @Singleton
    fun provideNozzleDao(database: DatabaseRoom): NozzleDao {
        return database.nozzleDao()
    }

    @Provides
    @Singleton
    fun providePressureDao(database: DatabaseRoom): PressureDao {
        return database.pressureDao()
    }

    @Provides
    @Singleton
    fun provideComponentDao(database: DatabaseRoom): ComponentDao {
        return database.componentDao()
    }

    @Provides
    @Singleton
    fun provideServiceDao(database: DatabaseRoom): ServiceDao {
        return database.serviceDao()
    }



}