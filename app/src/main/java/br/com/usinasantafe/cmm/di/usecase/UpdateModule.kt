package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.updatetable.*
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
    fun bindUpdateAtividade(usecase: IUpdateTableAtividade): UpdateTableAtividade

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
    fun bindUpdateTableParada(usecase: IUpdateTableParada): UpdateTableParada

    @Binds
    @Singleton
    fun bindUpdateTablePressaoBocal(usecase: IUpdateTablePressaoBocal): UpdateTablePressaoBocal

    @Binds
    @Singleton
    fun bindUpdateTablePropriedade(usecase: IUpdateTablePropriedade): UpdateTablePropriedade

    @Binds
    @Singleton
    fun bindUpdateTableRAtivParada(usecase: IUpdateTableRAtivParada): UpdateTableRAtivParada

    @Binds
    @Singleton
    fun bindUpdateTableREquipPneu(usecase: IUpdateTableREquipPneu): UpdateTableREquipPneu

    @Binds
    @Singleton
    fun bindUpdateTableRFuncaoAtivParada(usecase: IUpdateTableRFuncaoAtivParada): UpdateTableRFuncaoAtivParada

    @Binds
    @Singleton
    fun bindUpdateTableROSAtiv(usecase: IUpdateTableROSAtiv): UpdateTableROSAtiv

    @Binds
    @Singleton
    fun bindUpdateTableServico(usecase: IUpdateTableServico): UpdateTableServico

    @Binds
    @Singleton
    fun bindUpdateTableTurno(usecase: IUpdateTableTurno): UpdateTableTurno

}