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
    fun bindCheckOpenCheckList(usecase: ICheckOpenCheckList): CheckOpenCheckList

    @Binds
    @Singleton
    fun bindCheckUpdateCheckList(usecase: ICheckUpdateCheckList): CheckUpdateCheckList

    @Binds
    @Singleton
    fun bindGetItemCheckList(usecase: IGetItemCheckList): GetItemCheckList

    @Binds
    @Singleton
    fun bindSetRespItemCheckList(usecase: ISetRespItemCheckList): SetRespItemCheckList

    @Binds
    @Singleton
    fun bindDelLastRespItemCheckList(usecase: IDelLastRespItemCheckList): DelLastRespItemCheckList

}
