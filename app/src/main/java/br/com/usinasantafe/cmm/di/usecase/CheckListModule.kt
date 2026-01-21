package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.checkList.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface CheckListModule {

    @Binds
    @Singleton
    fun bindCheckSendCheckList(usecase: ICheckSendCheckList): CheckSendCheckList

    @Binds
    @Singleton
    fun bindCheckUpdateCheckList(usecase: ICheckUpdateCheckList): CheckUpdateCheckList

    @Binds
    @Singleton
    fun bindDelLastRespItemCheckList(usecase: IDelLastRespItemCheckList): DelLastRespItemCheckList

    @Binds
    @Singleton
    fun bindGetItemCheckList(usecase: IGetItemCheckList): GetItemCheckList

    @Binds
    @Singleton
    fun bindSendCheckList(usecase: ISendCheckList): SendCheckList

    @Binds
    @Singleton
    fun bindSetRespItemCheckList(usecase: ISetRespItemCheckList): SetRespItemCheckList

}
