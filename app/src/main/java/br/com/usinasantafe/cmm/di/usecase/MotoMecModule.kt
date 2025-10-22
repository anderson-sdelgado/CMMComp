package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.common.CheckNroOS
import br.com.usinasantafe.cmm.domain.usecases.common.FlowAppOpen
import br.com.usinasantafe.cmm.domain.usecases.common.ListActivity
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.common.ICheckNroOS
import br.com.usinasantafe.cmm.domain.usecases.common.IFlowAppOpen
import br.com.usinasantafe.cmm.domain.usecases.common.IListActivity
import br.com.usinasantafe.cmm.domain.usecases.common.IGetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.common.IListTurn
import br.com.usinasantafe.cmm.domain.usecases.common.ISetIdActivityCommon
import br.com.usinasantafe.cmm.domain.usecases.common.ISetNroOSCommon
import br.com.usinasantafe.cmm.domain.usecases.common.ListTurn
import br.com.usinasantafe.cmm.domain.usecases.common.SetIdActivityCommon
import br.com.usinasantafe.cmm.domain.usecases.common.SetNroOSCommon
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
    fun bindCheckRegOperator(usecase: ICheckRegOperator): CheckRegOperator

    @Binds
    @Singleton
    fun bindSetRegOperator(usecase: ISetRegOperator): SetRegOperator

    @Binds
    @Singleton
    fun bindGetDescrEquip(usecase: IGetDescrEquip): GetDescrEquip

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
    fun bindCheckFlowOpen(usecase: IFlowAppOpen): FlowAppOpen

    @Binds
    @Singleton
    fun bindGetNroOSHeader(usecase: IGetNroOSHeader): GetNroOSHeader

    @Binds
    @Singleton
    fun bindCheckHeaderSend(usecase: ICheckSendMotoMec): CheckSendMotoMec

    @Binds
    @Singleton
    fun bindSendHeader(usecase: ISendHeader): SendHeader

    @Binds
    @Singleton
    fun bindSetIdStopNote(usecase: ISetIdStopNote): SetIdStopNote

    @Binds
    @Singleton
    fun bindCheckNoteHeaderOpen(usecase: ICheckHasNoteHeaderOpen): CheckHasNoteHeaderOpen

    @Binds
    @Singleton
    fun bindHistoryList(usecase: IHistoryList): HistoryList

}