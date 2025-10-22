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
    fun bindGetToken(usecase: IGetToken): GetToken

    @Binds
    @Singleton
    fun bindGetItemMenuList(usecase: IListItemMenu): ListItemMenu

    @Binds
    @Singleton
    fun bindGetStopList(usecase: IListStop): ListStop

}