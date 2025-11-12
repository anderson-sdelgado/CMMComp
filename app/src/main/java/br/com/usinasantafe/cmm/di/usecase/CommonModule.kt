package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.common.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    @Singleton
    fun bindCheckAccessInitial(usecase: ICheckAccessInitial): CheckAccessInitial

    @Binds
    @Singleton
    fun bindFlowAppOpen(usecase: IFlowAppOpen): FlowAppOpen

    @Binds
    @Singleton
    fun bindGetDescrEquip(usecase: IGetDescrEquip): GetDescrEquip

    @Binds
    @Singleton
    fun bindGetToken(usecase: IGetToken): GetToken

}