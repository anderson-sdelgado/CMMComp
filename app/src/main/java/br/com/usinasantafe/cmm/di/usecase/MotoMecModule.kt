package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.motomec.*
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetStatusTranshipment
import br.com.usinasantafe.cmm.domain.usecases.motomec.IGetStatusTranshipment
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
    fun bindCheckRegOperator(usecase: ICheckRegOperator): CheckRegOperator

    @Binds
    @Singleton
    fun bindSetRegOperator(usecase: ISetRegOperator): SetRegOperator

    @Binds
    @Singleton
    fun bindSetIdEquip(usecase: ISetIdEquip): SetIdEquip

    @Binds
    @Singleton
    fun bindGetTurnList(usecase: IListTurn): ListTurn

    @Binds
    @Singleton
    fun bindSetIdTurn(usecase: ISetIdTurn): SetIdTurn

    @Binds
    @Singleton
    fun bindCheckNroOS(usecase: ICheckNroOS): CheckNroOS

    @Binds
    @Singleton
    fun bindSetNroOS(usecase: ISetNroOSCommon): SetNroOSCommon

    @Binds
    @Singleton
    fun bindGetActivityList(usecase: IListActivity): ListActivity

    @Binds
    @Singleton
    fun bindSetIdActivity(usecase: ISetIdActivityCommon): SetIdActivityCommon

    @Binds
    @Singleton
    fun bindCheckMeasure(usecase: ICheckHourMeter): CheckHourMeter

    @Binds
    @Singleton
    fun bindSetMeasureInitial(usecase: ISetHourMeter): SetHourMeter

    @Binds
    @Singleton
    fun bindGetNroOSHeader(usecase: IGetNroOSHeader): GetNroOSHeader

    @Binds
    @Singleton
    fun bindCheckHeaderSend(usecase: ICheckSendMotoMec): CheckSendMotoMec

    @Binds
    @Singleton
    fun bindSendHeader(usecase: ISendMotoMec): SendMotoMec

    @Binds
    @Singleton
    fun bindSetIdStopNote(usecase: ISetIdStopNote): SetIdStopNote

    @Binds
    @Singleton
    fun bindCheckNoteHeaderOpen(usecase: ICheckHasNoteMotoMec): CheckHasNoteMotoMec

    @Binds
    @Singleton
    fun bindHistoryList(usecase: IHistoryList): HistoryList

    @Binds
    @Singleton
    fun bindGetItemMenuList(usecase: IListItemMenu): ListItemMenu

    @Binds
    @Singleton
    fun bindGetStopList(usecase: IListStop): ListStop

    @Binds
    @Singleton
    fun bindGetFlowEquipNoteMotoMec(usecase: IGetFlowEquipNoteMotoMec): GetFlowEquipNoteMotoMec

    @Binds
    @Singleton
    fun bindGetStatusTranshipment(usecase: IGetStatusTranshipment): GetStatusTranshipment

    @Binds
    @Singleton
    fun bindCheckTypeLastNote(usecase: ICheckTypeLastNote): CheckTypeLastNote

    @Binds
    @Singleton
    fun bindSetNoteMotoMec(usecase: ISetNoteMotoMec): SetNoteMotoMec
}