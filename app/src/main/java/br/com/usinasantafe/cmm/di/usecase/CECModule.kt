package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.cec.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CECModule {

    @Binds
    @Singleton
    fun bindCheckCouplingTrailer(usecase: IHasCouplingTrailer): HasCouplingTrailer

    @Binds
    @Singleton
    fun bindGetStatusPreCEC(usecase: ISetDatePreCEC): SetDatePreCEC

    @Binds
    @Singleton
    fun bindUncouplingTrailer(usecase: IUncouplingTrailer): UncouplingTrailer

    @Binds
    @Singleton
    fun bindSetNroEquipPreCEC(usecase: ISetNroEquipPreCEC): SetNroEquipPreCEC

    @Binds
    @Singleton
    fun bindCheckAccessCertificate(usecase: ICheckAccessCertificate): CheckAccessCertificate

    @Binds
    @Singleton
    fun bindCheckIdRelease(usecase: ICheckIdRelease): CheckIdRelease

    @Binds
    @Singleton
    fun bindSetIdRelease(usecase: ISetIdRelease): SetIdRelease

}