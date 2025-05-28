package br.com.usinasantafe.cmm.di.datasource.room

import br.com.usinasantafe.cmm.external.room.datasource.stable.IActivityRoomDatasource
import br.com.usinasantafe.cmm.external.room.datasource.variable.IHeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindHeaderMotoMecRoomDatasource(dataSource: IHeaderMotoMecRoomDatasource): HeaderMotoMecRoomDatasource

}