package br.com.usinasantafe.cmm.di.repository

import br.com.usinasantafe.cmm.domain.repositories.variable.*
import br.com.usinasantafe.cmm.infra.repositories.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRepositoryModule {

    @Binds
    @Singleton
    fun bindConfigRepository(repository: IConfigRepository): ConfigRepository

    @Binds
    @Singleton
    fun bindHeaderMotoMecRepository(repository: IHeaderMotoMecRepository): HeaderMotoMecRepository

    @Binds
    @Singleton
    fun bindMenuRepository(repository: IMenuRepository): MenuRepository

    @Binds
    @Singleton
    fun bindNoteMotoMecRepository(repository: INoteMotoMecRepository): NoteMotoMecRepository

}