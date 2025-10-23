package br.com.usinasantafe.cmm.di.datasource.retrofit

import br.com.usinasantafe.cmm.external.retrofit.datasource.variable.*
import br.com.usinasantafe.cmm.infra.datasource.retrofit.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigRetrofitDatasource(dataSource: IConfigRetrofitDatasource): ConfigRetrofitDatasource

    @Binds
    @Singleton
    fun bindMotoMecRetrofitDatasource(dataSource: IMotoMecRetrofitDatasource): MotoMecRetrofitDatasource

    @Binds
    @Singleton
    fun bindCheckListRetrofitDatasource(dataSource: ICheckListRetrofitDatasource): CheckListRetrofitDatasource


}