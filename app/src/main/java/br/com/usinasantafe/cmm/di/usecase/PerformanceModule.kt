package br.com.usinasantafe.cmm.di.usecase


import br.com.usinasantafe.cmm.domain.usecases.performance.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PerformanceModule {

    @Binds
    @Singleton
    fun bindListPerformance(usecase: IListPerformance): ListPerformance

    @Binds
    @Singleton
    fun bindSetPerformance(usecase: ISetPerformance): SetPerformance

    @Binds
    @Singleton
    fun bindCheckPerformance(usecase: ICheckPerformance): CheckPerformance

    @Binds
    @Singleton
    fun bindCheckClosePerformance(usecase: ICheckClosePerformance): CheckClosePerformance

}