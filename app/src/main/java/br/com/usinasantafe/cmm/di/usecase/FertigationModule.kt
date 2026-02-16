package br.com.usinasantafe.cmm.di.usecase

import br.com.usinasantafe.cmm.domain.usecases.fertigation.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FertigationModule {

    @Binds
    @Singleton
    fun bindSetIdEquipMotorPump(usecase: ISetIdEquipMotorPump): SetIdEquipMotorPump

    @Binds
    @Singleton
    fun bindListNozzle(usecase: IListNozzle): ListNozzle

    @Binds
    @Singleton
    fun bindSetIdNozzle(usecase: ISetIdNozzle): SetIdNozzle

    @Binds
    @Singleton
    fun bindListPressure(usecase: IListPressure): ListPressure

    @Binds
    @Singleton
    fun bindListSpeed(usecase: IListSpeed): ListSpeed

    @Binds
    @Singleton
    fun bindSetSpeedPressure(usecase: ISetSpeedPressure): SetSpeedPressure

    @Binds
    @Singleton
    fun bindSetValuePressure(usecase: ISetValuePressure): SetValuePressure

}