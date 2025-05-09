package br.com.usinasantafe.cmm.di.datasource.room

import br.com.usinasantafe.cmm.external.room.datasource.stable.*
import br.com.usinasantafe.cmm.infra.datasource.room.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindAtividadeRoomDatasource(dataSource: IAtividadeRoomDatasource): AtividadeRoomDatasource

    @Binds
    @Singleton
    fun bindBocalRoomDatasource(dataSource: IBocalRoomDatasource): BocalRoomDatasource

    @Binds
    @Singleton
    fun bindColabRoomDatasource(dataSource: IColabRoomDatasource): ColabRoomDatasource

    @Binds
    @Singleton
    fun bindComponenteRoomDatasource(dataSource: IComponenteRoomDatasource): ComponenteRoomDatasource

    @Binds
    @Singleton
    fun bindFrenteRoomDatasource(dataSource: IFrenteRoomDatasource): FrenteRoomDatasource

    @Binds
    @Singleton
    fun bindItemCheckListRoomDatasource(dataSource: IItemCheckListRoomDatasource): ItemCheckListRoomDatasource

    @Binds
    @Singleton
    fun bindItemOSMecanRoomDatasource(dataSource: IItemOSMecanRoomDatasource): ItemOSMecanRoomDatasource

    @Binds
    @Singleton
    fun bindLeiraRoomDatasource(dataSource: ILeiraRoomDatasource): LeiraRoomDatasource

    @Binds
    @Singleton
    fun bindMotoMecRoomDatasource(dataSource: IMotoMecRoomDatasource): MotoMecRoomDatasource

    @Binds
    @Singleton
    fun bindOSRoomDatasource(dataSource: IOSRoomDatasource): OSRoomDatasource

    @Binds
    @Singleton
    fun bindParadaRoomDatasource(dataSource: IParadaRoomDatasource): ParadaRoomDatasource

    @Binds
    @Singleton
    fun bindPressaoBocalRoomDatasource(dataSource: IPressaoBocalRoomDatasource): PressaoBocalRoomDatasource

    @Binds
    @Singleton
    fun bindPropriedadeRoomDatasource(dataSource: IPropriedadeRoomDatasource): PropriedadeRoomDatasource

    @Binds
    @Singleton
    fun bindRAtivParadaRoomDatasource(dataSource: IRAtivParadaRoomDatasource): RAtivParadaRoomDatasource

    @Binds
    @Singleton
    fun bindREquipAtivRoomDatasource(dataSource: IREquipAtivRoomDatasource): REquipAtivRoomDatasource

    @Binds
    @Singleton
    fun bindREquipPneuRoomDatasource(dataSource: IREquipPneuRoomDatasource): REquipPneuRoomDatasource

    @Binds
    @Singleton
    fun bindRFuncaoAtivParadaRoomDatasource(dataSource: IRFuncaoAtivParadaRoomDatasource): RFuncaoAtivParadaRoomDatasource

    @Binds
    @Singleton
    fun bindROSAtivRoomDatasource(dataSource: IROSAtivRoomDatasource): ROSAtivRoomDatasource

    @Binds
    @Singleton
    fun bindServicoRoomDatasource(dataSource: IServicoRoomDatasource): ServicoRoomDatasource

    @Binds
    @Singleton
    fun bindTurnoRoomDatasource(dataSource: ITurnoRoomDatasource): TurnoRoomDatasource

}