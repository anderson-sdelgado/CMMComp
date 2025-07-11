package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.updateTable.*
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
    fun bindUpdateTableBocal(usecase: IUpdateTableBocal): UpdateTableBocal

    @Binds
    @Singleton
    fun bindUpdateTableColab(usecase: IUpdateTableColab): UpdateTableColab

    @Binds
    @Singleton
    fun bindUpdateTableComponente(usecase: IUpdateTableComponente): UpdateTableComponente

    @Binds
    @Singleton
    fun bindUpdateTableEquip(usecase: IUpdateTableEquipByIdEquip): UpdateTableEquipByIdEquip

    @Binds
    @Singleton
    fun bindUpdateTableFrente(usecase: IUpdateTableFrente): UpdateTableFrente

    @Binds
    @Singleton
    fun bindUpdateTableItemCheckList(usecase: IUpdateTableItemCheckList): UpdateTableItemCheckList

    @Binds
    @Singleton
    fun bindUpdateTableItemOSMecan(usecase: IUpdateTableItemOSMecan): UpdateTableItemOSMecan

    @Binds
    @Singleton
    fun bindUpdateTableLeira(usecase: IUpdateTableLeira): UpdateTableLeira

    @Binds
    @Singleton
    fun bindUpdateTableMotoMec(usecase: IUpdateTableMotoMec): UpdateTableMotoMec

    @Binds
    @Singleton
    fun bindUpdateTableOS(usecase: IUpdateTableOS): UpdateTableOS

    @Binds
    @Singleton
    fun bindUpdateTableStop(usecase: IUpdateTableStop): UpdateTableStop

    @Binds
    @Singleton
    fun bindUpdateTablePressaoBocal(usecase: IUpdateTablePressaoBocal): UpdateTablePressaoBocal

    @Binds
    @Singleton
    fun bindUpdateTablePropriedade(usecase: IUpdateTablePropriedade): UpdateTablePropriedade

    @Binds
    @Singleton
    fun bindUpdateTableRActivityStop(usecase: IUpdateTableRActivityStop): UpdateTableRActivityStop

    @Binds
    @Singleton
    fun bindUpdateTableREquipActivity(usecase: IUpdateTableREquipActivityByIdEquip): UpdateTableREquipActivityByIdEquip

    @Binds
    @Singleton
    fun bindUpdateTableREquipPneu(usecase: IUpdateTableREquipPneu): UpdateTableREquipPneu

    @Binds
    @Singleton
    fun bindUpdateTableFunctionActivityStop(usecase: IUpdateTableFunctionActivityStop): UpdateTableFunctionActivityStop

    @Binds
    @Singleton
    fun bindUpdateTableROSAtiv(usecase: IUpdateTableROSAtiv): UpdateTableROSAtiv

    @Binds
    @Singleton
    fun bindUpdateTableServico(usecase: IUpdateTableServico): UpdateTableServico

    @Binds
    @Singleton
    fun bindUpdateTableTurno(usecase: IUpdateTableTurn): UpdateTableTurn

}