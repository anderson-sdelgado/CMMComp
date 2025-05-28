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
    fun provideHeaderMotoMecDao(database: DatabaseRoom): HeaderMotoMecDao {
        return database.headerMotoMecDao()
    }

}