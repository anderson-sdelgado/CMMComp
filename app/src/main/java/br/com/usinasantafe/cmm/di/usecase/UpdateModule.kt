package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.update.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UpdateModule {

    @Binds
    @Singleton
    fun bindUpdateActivity(usecase: IUpdateTableActivity): UpdateTableActivity

    @Binds
    @Singleton
    fun bindUpdateTableColab(usecase: IUpdateTableColab): UpdateTableColab

    @Binds
    @Singleton
    fun bindUpdateTableEquip(usecase: IUpdateTableEquipByIdEquip): UpdateTableEquipByIdEquip

    @Binds
    @Singleton
    fun bindUpdateTableItemCheckList(usecase: IUpdateTableItemCheckListByNroEquip): UpdateTableItemCheckListByNroEquip

    @Binds
    @Singleton
    fun bindUpdateTableStop(usecase: IUpdateTableStop): UpdateTableStop

    @Binds
    @Singleton
    fun bindUpdateTableRActivityStop(usecase: IUpdateTableRActivityStop): UpdateTableRActivityStop

    @Binds
    @Singleton
    fun bindUpdateTableREquipActivity(usecase: IUpdateTableREquipActivityByIdEquip): UpdateTableREquipActivityByIdEquip

    @Binds
    @Singleton
    fun bindUpdateTableFunctionActivity(usecase: IUpdateTableFunctionActivity): UpdateTableFunctionActivity

    @Binds
    @Singleton
    fun bindUpdateTableFunctionStop(usecase: IUpdateTableFunctionStop): UpdateTableFunctionStop

    @Binds
    @Singleton
    fun bindUpdateTableItemMenu(usecase: IUpdateTableItemMenu): UpdateTableItemMenu

    @Binds
    @Singleton
    fun bindUpdateTableTurn(usecase: IUpdateTableTurn): UpdateTableTurn

}