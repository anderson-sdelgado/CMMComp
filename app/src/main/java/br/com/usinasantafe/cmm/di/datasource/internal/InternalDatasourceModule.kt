package br.com.usinasantafe.cmm.di.datasource.internal

import br.com.usinasantafe.cmm.external.internal.IItemMenuInternalDatasource
import br.com.usinasantafe.cmm.infra.datasource.internal.ItemMenuInternalDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface InternalDatasourceModule {


    @Binds
    @Singleton
    fun bindItemMenuInternalDatasource(dataSource: IItemMenuInternalDatasource): ItemMenuInternalDatasource

}