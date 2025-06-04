package br.com.usinasantafe.cmm.di.datasource.sharedpreferences

import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.*
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedPreferencesDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigSharedPreferencesDatasource(dataSource: IConfigSharedPreferencesDatasource): ConfigSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindHeaderMotoMecSharedPreferencesDatasource(dataSource: IHeaderMotoMecSharedPreferencesDatasource): HeaderMotoMecSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindNoteMotoMecSharedPreferencesDatasource(dataSource: INoteMotoMecSharedPreferencesDatasource): NoteMotoMecSharedPreferencesDatasource

}