package br.com.usinasantafe.cmm.di.repository

import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.infra.repositories.variable.IConfigRepository
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

}