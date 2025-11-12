package br.com.usinasantafe.cmm.di.datasource.room

import br.com.usinasantafe.cmm.external.room.datasource.stable.*
import br.com.usinasantafe.cmm.infra.datasource.room.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindActivityRoomDatasource(dataSource: IActivityRoomDatasource): ActivityRoomDatasource

    @Binds
    @Singleton
    fun bindColabRoomDatasource(dataSource: IColabRoomDatasource): ColabRoomDatasource

    @Binds
    @Singleton
    fun bindEquipRoomDatasource(dataSource: IEquipRoomDatasource): EquipRoomDatasource

    @Binds
    @Singleton
    fun bindFunctionActivityRoomDatasource(dataSource: IFunctionActivityRoomDatasource): FunctionActivityRoomDatasource

    @Binds
    @Singleton
    fun bindFunctionStopRoomDatasource(dataSource: IFunctionStopRoomDatasource): FunctionStopRoomDatasource

    @Binds
    @Singleton
    fun bindItemCheckListRoomDatasource(dataSource: IItemCheckListRoomDatasource): ItemCheckListRoomDatasource

    @Binds
    @Singleton
    fun bindItemMenuRoomDatasource(dataSource: IItemMenuRoomDatasource): ItemMenuRoomDatasource

    @Binds
    @Singleton
    fun bindOSRoomDatasource(dataSource: IOSRoomDatasource): OSRoomDatasource

    @Binds
    @Singleton
    fun bindRActivityStopRoomDatasource(dataSource: IRActivityStopRoomDatasource): RActivityStopRoomDatasource

    @Binds
    @Singleton
    fun bindREquipActivityRoomDatasource(dataSource: IREquipActivityRoomDatasource): REquipActivityRoomDatasource

    @Binds
    @Singleton
    fun bindROSActivityRoomDatasource(dataSource: IROSActivityRoomDatasource): ROSActivityRoomDatasource

    @Binds
    @Singleton
    fun bindStopRoomDatasource(dataSource: IStopRoomDatasource): StopRoomDatasource

    @Binds
    @Singleton
    fun bindTurnRoomDatasource(dataSource: ITurnRoomDatasource): TurnRoomDatasource

}