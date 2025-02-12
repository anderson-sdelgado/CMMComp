package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.updatetable.update.*
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
    fun bindUpdateAtividade(usecase: IUpdateAtividade): UpdateAtividade

    @Binds
    @Singleton
    fun bindUpdateBocal(usecase: IUpdateBocal): UpdateBocal

    @Binds
    @Singleton
    fun bindUpdateColab(usecase: IUpdateColab): UpdateColab

    @Binds
    @Singleton
    fun bindUpdateComponente(usecase: IUpdateComponente): UpdateComponente

    @Binds
    @Singleton
    fun bindUpdateEquipPneu(usecase: IUpdateEquipPneu): UpdateEquipPneu

    @Binds
    @Singleton
    fun bindUpdateEquipSeg(usecase: IUpdateEquipSeg): UpdateEquipSeg

    @Binds
    @Singleton
    fun bindUpdateFrente(usecase: IUpdateFrente): UpdateFrente

    @Binds
    @Singleton
    fun bindUpdateItemCheckList(usecase: IUpdateItemCheckList): UpdateItemCheckList

    @Binds
    @Singleton
    fun bindUpdateItemOSMecan(usecase: IUpdateItemOSMecan): UpdateItemOSMecan

    @Binds
    @Singleton
    fun bindUpdateLeira(usecase: IUpdateLeira): UpdateLeira

    @Binds
    @Singleton
    fun bindUpdateMotoMec(usecase: IUpdateMotoMec): UpdateMotoMec

    @Binds
    @Singleton
    fun bindUpdateOS(usecase: IUpdateOS): UpdateOS

    @Binds
    @Singleton
    fun bindUpdateParada(usecase: IUpdateParada): UpdateParada

    @Binds
    @Singleton
    fun bindUpdatePressaoBocal(usecase: IUpdatePressaoBocal): UpdatePressaoBocal

    @Binds
    @Singleton
    fun bindUpdatePropriedade(usecase: IUpdatePropriedade): UpdatePropriedade

    @Binds
    @Singleton
    fun bindUpdateRAtivParada(usecase: IUpdateRAtivParada): UpdateRAtivParada

    @Binds
    @Singleton
    fun bindUpdateREquipPneu(usecase: IUpdateREquipPneu): UpdateREquipPneu

    @Binds
    @Singleton
    fun bindUpdateRFuncaoAtiv(usecase: IUpdateRFuncaoAtiv): UpdateRFuncaoAtiv

    @Binds
    @Singleton
    fun bindUpdateROSAtiv(usecase: IUpdateROSAtiv): UpdateROSAtiv

    @Binds
    @Singleton
    fun bindUpdateServico(usecase: IUpdateServico): UpdateServico

    @Binds
    @Singleton
    fun bindUpdateTurno(usecase: IUpdateTurno): UpdateTurno

}