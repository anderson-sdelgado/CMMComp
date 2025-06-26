package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.common.IListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.common.IListStop
import br.com.usinasantafe.cmm.domain.usecases.common.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.common.ListStop
import br.com.usinasantafe.cmm.domain.usecases.note.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NoteModule {

    @Binds
    @Singleton
    fun bindGetItemMenuList(usecase: IListItemMenu): ListItemMenu

    @Binds
    @Singleton
    fun bindGetStopList(usecase: IListStop): ListStop

    @Binds
    @Singleton
    fun bindSetIdStopNote(usecase: ISetIdStopNote): SetIdStopNote

    @Binds
    @Singleton
    fun bindCheckNoteHeaderOpen(usecase: ICheckHasNoteHeaderOpen): CheckHasNoteHeaderOpen

}