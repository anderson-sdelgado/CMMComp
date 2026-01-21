package br.com.usinasantafe.cmm.di.datasource.room

import br.com.usinasantafe.cmm.external.room.datasource.variable.*
import br.com.usinasantafe.cmm.infra.datasource.room.variable.*
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
    fun bindNoteMechanicRoomDatasource(dataSource: IMechanicRoomDatasource): MechanicRoomDatasource

    @Binds
    @Singleton
    fun bindNoteMotoMecRoomDatasource(dataSource: IItemMotoMecRoomDatasource): ItemMotoMecRoomDatasource

    @Binds
    @Singleton
    fun bindRespItemCheckListRoomDatasource(dataSource: IItemRespCheckListRoomDatasource): ItemRespCheckListRoomDatasource

    @Binds
    @Singleton
    fun bindInputCompostingRoomDatasource(dataSource: IInputCompostingRoomDatasource): InputCompostingRoomDatasource

    @Binds
    @Singleton
    fun bindCompoundCompostingRoomDatasource(dataSource: ICompoundCompostingRoomDatasource): CompoundCompostingRoomDatasource

    @Binds
    @Singleton
    fun bindPerformanceRoomDatasource(dataSource: IPerformanceRoomDatasource): PerformanceRoomDatasource

}