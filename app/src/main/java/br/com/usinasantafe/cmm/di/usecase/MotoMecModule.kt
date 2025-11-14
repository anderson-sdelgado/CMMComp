package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.motomec.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MotoMecModule {

    @Binds
    @Singleton
    fun bindCheckCouplingTrailer(usecase: ICheckCouplingTrailer): CheckCouplingTrailer

    @Binds
    @Singleton
    fun bindCheckHasNoteMotoMec(usecase: ICheckHasNoteMotoMec): CheckHasNoteMotoMec

    @Binds
    @Singleton
    fun bindCheckMeasure(usecase: ICheckHourMeter): CheckHourMeter

    @Binds
    @Singleton
    fun bindCheckNroOS(usecase: ICheckNroOS): CheckNroOS

    @Binds
    @Singleton
    fun bindCheckRegOperator(usecase: ICheckRegOperator): CheckRegOperator

    @Binds
    @Singleton
    fun bindCheckSendMotoMec(usecase: ICheckSendMotoMec): CheckSendMotoMec

    @Binds
    @Singleton
    fun bindCheckTypeLastNote(usecase: ICheckTypeLastNote): CheckTypeLastNote

    @Binds
    @Singleton
    fun bindGetFlowEquipNoteMotoMec(usecase: IGetFlowEquipNoteMotoMec): GetFlowEquipNoteMotoMec

    @Binds
    @Singleton
    fun bindGetNroOSHeader(usecase: IGetNroOSHeader): GetNroOSHeader

    @Binds
    @Singleton
    fun bindGetStatusTranshipment(usecase: IGetStatusTranshipment): GetStatusTranshipment

    @Binds
    @Singleton
    fun bindListActivity(usecase: IListActivity): ListActivity

    @Binds
    @Singleton
    fun bindListHistory(usecase: IListHistory): ListHistory

    @Binds
    @Singleton
    fun bindListItemMenu(usecase: IListItemMenu): ListItemMenu

    @Binds
    @Singleton
    fun bindListStop(usecase: IListStop): ListStop

    @Binds
    @Singleton
    fun bindListTurn(usecase: IListTurn): ListTurn

    @Binds
    @Singleton
    fun bindSendHeader(usecase: ISendMotoMec): SendMotoMec

    @Binds
    @Singleton
    fun bindSetHourMeter(usecase: ISetHourMeter): SetHourMeter

    @Binds
    @Singleton
    fun bindSetIdActivity(usecase: ISetIdActivityCommon): SetIdActivityCommon

    @Binds
    @Singleton
    fun bindSetIdEquip(usecase: ISetIdEquip): SetIdEquip

    @Binds
    @Singleton
    fun bindSetIdStopNote(usecase: ISetIdStopNote): SetIdStopNote

    @Binds
    @Singleton
    fun bindSetIdTurn(usecase: ISetIdTurn): SetIdTurn

    @Binds
    @Singleton
    fun bindSetNoteMotoMec(usecase: ISetNoteMotoMec): SetNoteMotoMec

    @Binds
    @Singleton
    fun bindSetNroOS(usecase: ISetNroOS): SetNroOS

    @Binds
    @Singleton
    fun bindSetRegOperator(usecase: ISetRegOperator): SetRegOperator

    @Binds
    @Singleton
    fun bindUncouplingTrailer(usecase: IUncouplingTrailer): UncouplingTrailer

}