package br.com.usinasantafe.cmm.di.external.room

import br.com.usinasantafe.cmm.external.room.DatabaseRoom
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
    fun provideNoteMechanicDao(database: DatabaseRoom): NoteMechanicDao {
        return database.noteMechanicDao()
    }
    
    @Provides
    @Singleton
    fun provideNoteMotoMecDao(database: DatabaseRoom): NoteMotoMecDao {
        return database.noteMotoMecDao()
    }

    @Provides
    @Singleton
    fun provideRespCheckListDao(database: DatabaseRoom): RespItemCheckListDao {
        return database.respItemCheckListDao()
    }

}
