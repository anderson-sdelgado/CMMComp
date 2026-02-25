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
    fun bindActivityRepository(repository: IActivityRepository): ActivityRepository

    @Binds
    @Singleton
    fun bindColabRepository(repository: IColabRepository): ColabRepository

    @Binds
    @Singleton
    fun bindEquipRepository(repository: IEquipRepository): EquipRepository

    @Binds
    @Singleton
    fun bindFunctionActivityRepository(repository: IFunctionActivityRepository): FunctionActivityRepository

    @Binds
    @Singleton
    fun bindFunctionStopRepository(repository: IFunctionStopRepository): FunctionStopRepository

    @Binds
    @Singleton
    fun bindItemCheckListRepository(repository: IItemCheckListRepository): ItemCheckListRepository

    @Binds
    @Singleton
    fun bindItemMenuRepository(repository: IItemMenuRepository): ItemMenuRepository

    @Binds
    @Singleton
    fun bindOSRepository(repository: IOSRepository): OSRepository

    @Binds
    @Singleton
    fun bindRActivityStopRepository(repository: IRActivityStopRepository): RActivityStopRepository

    @Binds
    @Singleton
    fun bindREquipActivityRepository(repository: IREquipActivityRepository): REquipActivityRepository

    @Binds
    @Singleton
    fun bindRItemMenuStopRepository(repository: IRItemMenuStopRepository): RItemMenuStopRepository

    @Binds
    @Singleton
    fun bindROSActivityRepository(repository: IROSActivityRepository): ROSActivityRepository

    @Binds
    @Singleton
    fun bindStopRepository(repository: IStopRepository): StopRepository

    @Binds
    @Singleton
    fun bindTurnRepository(repository: ITurnRepository): TurnRepository

    @Binds
    @Singleton
    fun bindNozzleRepository(repository: INozzleRepository): NozzleRepository

    @Binds
    @Singleton
    fun bindPressureRepository(repository: IPressureRepository): PressureRepository

    @Binds
    @Singleton
    fun bindComponentRepository(repository: IComponentRepository): ComponentRepository

    @Binds
    @Singleton
    fun bindServiceRepository(repository: IServiceRepository): ServiceRepository

    @Binds
    @Singleton
    fun bindItemOSMechanicRepository(repository: IItemOSMechanicRepository): ItemOSMechanicRepository

}