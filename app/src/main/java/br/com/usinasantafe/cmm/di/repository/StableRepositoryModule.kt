package br.com.usinasantafe.cmm.di.repository

import br.com.usinasantafe.cmm.domain.repositories.stable.*
import br.com.usinasantafe.cmm.infra.repositories.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRepositoryModule {

    @Binds
    @Singleton
    fun bindAtividadeRepository(repository: IAtividadeRepository): AtividadeRepository

    @Binds
    @Singleton
    fun bindBocalRepository(repository: IBocalRepository): BocalRepository

    @Binds
    @Singleton
    fun bindColabRepository(repository: IColabRepository): ColabRepository

    @Binds
    @Singleton
    fun bindComponenteRepository(repository: IComponenteRepository): ComponenteRepository

    @Binds
    @Singleton
    fun bindFrenteRepository(repository: IFrenteRepository): FrenteRepository

    @Binds
    @Singleton
    fun bindItemCheckListRepository(repository: IItemCheckListRepository): ItemCheckListRepository

    @Binds
    @Singleton
    fun bindItemOSMecanRepository(repository: IItemOSMecanRepository): ItemOSMecanRepository

    @Binds
    @Singleton
    fun bindLeiraRepository(repository: ILeiraRepository): LeiraRepository

    @Binds
    @Singleton
    fun bindMotoMecRepository(repository: IMotoMecRepository): MotoMecRepository

    @Binds
    @Singleton
    fun bindOSRepository(repository: IOSRepository): OSRepository

    @Binds
    @Singleton
    fun bindParadaRepository(repository: IParadaRepository): ParadaRepository

    @Binds
    @Singleton
    fun bindPressaoBocalRepository(repository: IPressaoBocalRepository): PressaoBocalRepository

    @Binds
    @Singleton
    fun bindPropriedadeRepository(repository: IPropriedadeRepository): PropriedadeRepository

    @Binds
    @Singleton
    fun bindRAtivParadaRepository(repository: IRAtivParadaRepository): RAtivParadaRepository

    @Binds
    @Singleton
    fun bindREquipPneuRepository(repository: IREquipPneuRepository): REquipPneuRepository

    @Binds
    @Singleton
    fun bindRFuncaoAtivRepository(repository: IRFuncaoAtivParadaRepository): RFuncaoAtivParadaRepository

    @Binds
    @Singleton
    fun bindROSAtivRepository(repository: IROSAtivRepository): ROSAtivRepository

    @Binds
    @Singleton
    fun bindServicoRepository(repository: IServicoRepository): ServicoRepository

    @Binds
    @Singleton
    fun bindTurnoRepository(repository: ITurnoRepository): TurnoRepository

}