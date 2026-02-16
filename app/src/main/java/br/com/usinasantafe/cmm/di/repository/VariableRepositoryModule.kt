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
    fun bindCheckListRepository(repository: ICheckListRepository): CheckListRepository

    @Binds
    @Singleton
    fun bindConfigRepository(repository: IConfigRepository): ConfigRepository

    @Binds
    @Singleton
    fun bindMechanicRepository(repository: IMechanicRepository): MechanicRepository

    @Binds
    @Singleton
    fun bindMotoMecRepository(repository: IMotoMecRepository): MotoMecRepository

    @Binds
    @Singleton
    fun bindCECRepository(repository: ICECRepository): CECRepository

    @Binds
    @Singleton
    fun bindCompostingRepository(repository: ICompostingRepository): CompostingRepository

    @Binds
    @Singleton
    fun bindPerformanceRepository(repository: IPerformanceRepository): PerformanceRepository

    @Binds
    @Singleton
    fun bindFertigationRepository(repository: IFertigationRepository): FertigationRepository



}