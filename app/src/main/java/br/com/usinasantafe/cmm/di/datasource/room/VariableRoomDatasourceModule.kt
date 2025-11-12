package br.com.usinasantafe.cmm.di.datasource.room

import br.com.usinasantafe.cmm.external.room.datasource.variable.*
import br.com.usinasantafe.cmm.infra.datasource.room.variable.*
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel
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
    fun bindHeaderCheckListRoomDatasource(dataSource: IHeaderCheckListRoomDatasource): HeaderCheckListRoomDatasource

    @Binds
    @Singleton
    fun bindHeaderMotoMecRoomDatasource(dataSource: IHeaderMotoMecRoomDatasource): HeaderMotoMecRoomDatasource

    @Binds
    @Singleton
    fun bindNoteMechanicRoomDatasource(dataSource: INoteMechanicRoomDatasource): NoteMechanicRoomDatasource

    @Binds
    @Singleton
    fun bindNoteMotoMecRoomDatasource(dataSource: INoteMotoMecRoomDatasource): NoteMotoMecRoomDatasource

    @Binds
    @Singleton
    fun bindRespItemCheckListRoomDatasource(dataSource: IRespItemCheckListRoomDatasource): RespItemCheckListRoomDatasource

}