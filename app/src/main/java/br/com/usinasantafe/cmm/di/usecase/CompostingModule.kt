package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.composting.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CompostingModule {

    @Binds
    @Singleton
    fun bindCheckInitialLoading(usecase: IHasCompostingInputLoadSentOpen): HasCompostingInputLoadSentOpen

    @Binds
    @Singleton
    fun bindCheckWill(usecase: ICheckWill): CheckWill

    @Binds
    @Singleton
    fun bindGetFlowComposting(usecase: IGetFlowComposting): GetFlowComposting

}