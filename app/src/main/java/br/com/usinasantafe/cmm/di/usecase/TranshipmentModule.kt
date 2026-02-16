package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.transhipment.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TranshipmentModule {

    @Binds
    @Singleton
    fun bindGetStatusTranshipment(usecase: IGetStatusTranshipment): GetStatusTranshipment

    @Binds
    @Singleton
    fun bindSetNroTranshipment(usecase: ISetNroEquipTranshipment): SetNroEquipTranshipment



}