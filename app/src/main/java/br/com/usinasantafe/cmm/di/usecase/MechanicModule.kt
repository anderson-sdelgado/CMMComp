package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.mechanic.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MechanicModule {

    @Binds
    @Singleton
    fun bindCheckHasNoteOpenMechanic(usecase: IHasNoteOpen): HasNoteOpen

    @Binds
    @Singleton
    fun bindFinishNoteMechanical(usecase: IFinishNote): FinishNote

    @Binds
    @Singleton
    fun bindCheckNroOS(usecase: ICheckNroOS): CheckNroOS

}