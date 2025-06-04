package br.com.usinasantafe.cmm.di.external.retrofit

import br.com.usinasantafe.cmm.di.persistence.DefaultRetrofit
import br.com.usinasantafe.cmm.di.persistence.OSApiDefault
import br.com.usinasantafe.cmm.di.persistence.OSApiShortTimeout
import br.com.usinasantafe.cmm.di.persistence.ShortTimeoutRetrofit
import br.com.usinasantafe.cmm.external.retrofit.api.stable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRetrofitModule {

    @Provides
    @Singleton
    fun atividadeApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ActivityApi = retrofit.create(ActivityApi::class.java)

    @Provides
    @Singleton
    fun bocalApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): BocalApi = retrofit.create(BocalApi::class.java)

    @Provides
    @Singleton
    fun colabApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ColabApi = retrofit.create(ColabApi::class.java)

    @Provides
    @Singleton
    fun componenteApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ComponenteApi = retrofit.create(ComponenteApi::class.java)

    @Provides
    @Singleton
    fun equipApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): EquipApi = retrofit.create(EquipApi::class.java)

    @Provides
    @Singleton
    fun frenteApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): FrenteApi = retrofit.create(FrenteApi::class.java)

    @Provides
    @Singleton
    fun itemCheckListApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ItemCheckListApi = retrofit.create(ItemCheckListApi::class.java)

    @Provides
    @Singleton
    fun itemOSMecanApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ItemOSMecanApi = retrofit.create(ItemOSMecanApi::class.java)

    @Provides
    @Singleton
    fun leiraApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): LeiraApi = retrofit.create(LeiraApi::class.java)

    @Provides
    @Singleton
    fun motoMecApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): MotoMecApi = retrofit.create(MotoMecApi::class.java)

    @Provides
    @Singleton
    @OSApiDefault
    fun provideOSApiDefault(
        @DefaultRetrofit retrofit: Retrofit
    ): OSApi = retrofit.create(OSApi::class.java)

    @Provides
    @Singleton
    @OSApiShortTimeout
    fun provideOSApiShortTimeout(
        @ShortTimeoutRetrofit retrofit: Retrofit
    ): OSApi = retrofit.create(OSApi::class.java)

    @Provides
    @Singleton
    fun paradaApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): StopApi = retrofit.create(StopApi::class.java)

    @Provides
    @Singleton
    fun pressaoBocalApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): PressaoBocalApi = retrofit.create(PressaoBocalApi::class.java)

    @Provides
    @Singleton
    fun propriedadeApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): PropriedadeApi = retrofit.create(PropriedadeApi::class.java)

    @Provides
    @Singleton
    fun rAtivParadaApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): RActivityStopApi = retrofit.create(RActivityStopApi::class.java)

    @Provides
    @Singleton
    fun rEquipAtivApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): REquipActivityApi = retrofit.create(REquipActivityApi::class.java)

    @Provides
    @Singleton
    fun rEquipPneuApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): REquipPneuApi = retrofit.create(REquipPneuApi::class.java)

    @Provides
    @Singleton
    fun rFuncaoAtivParadaApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): RFuncaoAtivParadaApi = retrofit.create(RFuncaoAtivParadaApi::class.java)

    @Provides
    @Singleton
    fun rOSAtivApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ROSActivityApi = retrofit.create(ROSActivityApi::class.java)

    @Provides
    @Singleton
    fun servicoApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ServicoApi = retrofit.create(ServicoApi::class.java)

    @Provides
    @Singleton
    fun turnoApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): TurnApi = retrofit.create(TurnApi::class.java)

}