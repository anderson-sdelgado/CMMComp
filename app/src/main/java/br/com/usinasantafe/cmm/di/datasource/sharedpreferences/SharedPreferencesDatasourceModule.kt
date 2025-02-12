package br.com.usinasantafe.cmm.di.datasource.sharedpreferences

import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
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

}