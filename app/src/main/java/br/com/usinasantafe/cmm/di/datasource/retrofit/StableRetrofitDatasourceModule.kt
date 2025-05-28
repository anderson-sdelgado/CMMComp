package br.com.usinasantafe.cmm.di.datasource.retrofit

import br.com.usinasantafe.cmm.external.retrofit.datasource.stable.*
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindAtividadeRetrofitDatasource(dataSource: IActivityRetrofitDatasource): ActivityRetrofitDatasource

    @Binds
    @Singleton
    fun bindBocalRetrofitDatasource(dataSource: IBocalRetrofitDatasource): BocalRetrofitDatasource

    @Binds
    @Singleton
    fun bindColabRetrofitDatasource(dataSource: IColabRetrofitDatasource): ColabRetrofitDatasource

    @Binds
    @Singleton
    fun bindComponenteRetrofitDatasource(dataSource: IComponenteRetrofitDatasource): ComponenteRetrofitDatasource

    @Binds
    @Singleton
    fun bindEquipRetrofitDatasource(dataSource: IEquipRetrofitDatasource): EquipRetrofitDatasource

    @Binds
    @Singleton
    fun bindFrenteRetrofitDatasource(dataSource: IFrenteRetrofitDatasource): FrenteRetrofitDatasource

    @Binds
    @Singleton
    fun bindItemCheckListRetrofitDatasource(dataSource: IItemCheckListRetrofitDatasource): ItemCheckListRetrofitDatasource

    @Binds
    @Singleton
    fun bindItemOSMecanRetrofitDatasource(dataSource: IItemOSMecanRetrofitDatasource): ItemOSMecanRetrofitDatasource

    @Binds
    @Singleton
    fun bindLeiraRetrofitDatasource(dataSource: ILeiraRetrofitDatasource): LeiraRetrofitDatasource

    @Binds
    @Singleton
    fun bindMotoMecRetrofitDatasource(dataSource: IMotoMecRetrofitDatasource): MotoMecRetrofitDatasource

    @Binds
    @Singleton
    fun bindOSRetrofitDatasource(dataSource: IOSRetrofitDatasource): OSRetrofitDatasource

    @Binds
    @Singleton
    fun bindParadaRetrofitDatasource(dataSource: IParadaRetrofitDatasource): ParadaRetrofitDatasource

    @Binds
    @Singleton
    fun bindPressaoBocalRetrofitDatasource(dataSource: IPressaoBocalRetrofitDatasource): PressaoBocalRetrofitDatasource

    @Binds
    @Singleton
    fun bindPropriedadeRetrofitDatasource(dataSource: IPropriedadeRetrofitDatasource): PropriedadeRetrofitDatasource

    @Binds
    @Singleton
    fun bindRAtivParadaRetrofitDatasource(dataSource: IRAtivParadaRetrofitDatasource): RAtivParadaRetrofitDatasource

    @Binds
    @Singleton
    fun bindREquipAtivRetrofitDatasource(dataSource: IREquipActivityRetrofitDatasource): REquipActivityRetrofitDatasource

    @Binds
    @Singleton
    fun bindREquipPneuRetrofitDatasource(dataSource: IREquipPneuRetrofitDatasource): REquipPneuRetrofitDatasource

    @Binds
    @Singleton
    fun bindRFuncaoAtivParadaRetrofitDatasource(dataSource: IRFuncaoAtivParadaRetrofitDatasource): RFuncaoAtivParadaRetrofitDatasource

    @Binds
    @Singleton
    fun bindROSAtivRetrofitDatasource(dataSource: IROSActivityRetrofitDatasource): ROSActivityRetrofitDatasource

    @Binds
    @Singleton
    fun bindServicoRetrofitDatasource(dataSource: IServicoRetrofitDatasource): ServicoRetrofitDatasource

    @Binds
    @Singleton
    fun bindTurnoRetrofitDatasource(dataSource: ITurnRetrofitDatasource): TurnoRetrofitDatasource

}