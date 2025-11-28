package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.lib.IStartWorkManager
import br.com.usinasantafe.cmm.lib.StartWorkManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BackgroundModule {

    @Binds
    @Singleton
    fun bindStartWorkInitial(usecase: IStartWorkManager): StartWorkManager

}