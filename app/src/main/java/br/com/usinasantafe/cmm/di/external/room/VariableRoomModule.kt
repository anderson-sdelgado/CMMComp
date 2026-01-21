package br.com.usinasantafe.cmm.di.external.room

import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableRoomModule {

    @Provides
    @Singleton
    fun provideHeaderCheckListDao(database: DatabaseRoom): HeaderCheckListDao {
        return database.headerCheckListDao()
    }

    @Provides
    @Singleton
    fun provideHeaderMotoMecDao(database: DatabaseRoom): HeaderMotoMecDao {
        return database.headerMotoMecDao()
    }

    @Provides
    @Singleton
    fun provideNoteMechanicDao(database: DatabaseRoom): MechanicDao {
        return database.noteMechanicDao()
    }
    
    @Provides
    @Singleton
    fun provideNoteMotoMecDao(database: DatabaseRoom): ItemMotoMecDao {
        return database.noteMotoMecDao()
    }

    @Provides
    @Singleton
    fun provideRespCheckListDao(database: DatabaseRoom): ItemRespCheckListDao {
        return database.respItemCheckListDao()
    }

    @Provides
    @Singleton
    fun provideInputComposting(database: DatabaseRoom): InputCompostingDao {
        return database.inputCompostingDao()
    }

    @Provides
    @Singleton
    fun provideCompoundComposting(database: DatabaseRoom): CompoundCompostingDao {
        return database.compoundCompostingDao()
    }

    @Provides
    @Singleton
    fun providePerformance(database: DatabaseRoom): PerformanceDao {
        return database.performanceDao()
    }
}
