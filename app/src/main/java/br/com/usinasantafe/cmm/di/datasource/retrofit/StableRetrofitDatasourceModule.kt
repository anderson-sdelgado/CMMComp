package br.com.usinasantafe.cmm.di.datasource.retrofit

import br.com.usinasantafe.cmm.external.retrofit.datasource.stable.*
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindActivityRetrofitDatasource(dataSource: IActivityRetrofitDatasource): ActivityRetrofitDatasource

    @Binds
    @Singleton
    fun bindColabRetrofitDatasource(dataSource: IColabRetrofitDatasource): ColabRetrofitDatasource

    @Binds
    @Singleton
    fun bindEquipRetrofitDatasource(dataSource: IEquipRetrofitDatasource): EquipRetrofitDatasource

    @Binds
    @Singleton
    fun bindFunctionActivityRetrofitDatasource(dataSource: IFunctionActivityRetrofitDatasource): FunctionActivityRetrofitDatasource

    @Binds
    @Singleton
    fun bindFunctionStopRetrofitDatasource(dataSource: IFunctionStopRetrofitDatasource): FunctionStopRetrofitDatasource

    @Binds
    @Singleton
    fun bindItemCheckListRetrofitDatasource(dataSource: IItemCheckListRetrofitDatasource): ItemCheckListRetrofitDatasource

    @Binds
    @Singleton
    fun bindItemMenuRetrofitDatasource(dataSource: IItemMenuRetrofitDatasource): ItemMenuRetrofitDatasource

    @Binds
    @Singleton
    fun bindOSRetrofitDatasource(dataSource: IOSRetrofitDatasource): OSRetrofitDatasource

    @Binds
    @Singleton
    fun bindRActivityStopRetrofitDatasource(dataSource: IRActivityStopRetrofitDatasource): RActivityStopRetrofitDatasource

    @Binds
    @Singleton
    fun bindREquipActivityRetrofitDatasource(dataSource: IREquipActivityRetrofitDatasource): REquipActivityRetrofitDatasource

    @Binds
    @Singleton
    fun bindROSActivityRetrofitDatasource(dataSource: IROSActivityRetrofitDatasource): ROSActivityRetrofitDatasource

    @Binds
    @Singleton
    fun bindStopRetrofitDatasource(dataSource: IStopRetrofitDatasource): StopRetrofitDatasource

    @Binds
    @Singleton
    fun bindTurnRetrofitDatasource(dataSource: ITurnRetrofitDatasource): TurnoRetrofitDatasource

}