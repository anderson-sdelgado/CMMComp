package br.com.usinasantafe.cmm.di.external.retrofit

import br.com.usinasantafe.cmm.di.provider.DefaultRetrofit
import br.com.usinasantafe.cmm.di.provider.OSApiDefault
import br.com.usinasantafe.cmm.di.provider.OSApiShortTimeout
import br.com.usinasantafe.cmm.di.provider.ShortTimeoutRetrofit
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
    fun activityApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ActivityApi = retrofit.create(ActivityApi::class.java)

    @Provides
    @Singleton
    fun colabApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ColabApi = retrofit.create(ColabApi::class.java)

    @Provides
    @Singleton
    fun equipApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): EquipApi = retrofit.create(EquipApi::class.java)

    @Provides
    @Singleton
    fun functionActivityRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): FunctionActivityApi = retrofit.create(FunctionActivityApi::class.java)

    @Provides
    @Singleton
    fun functionStopRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): FunctionStopApi = retrofit.create(FunctionStopApi::class.java)

    @Provides
    @Singleton
    fun itemCheckListApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ItemCheckListApi = retrofit.create(ItemCheckListApi::class.java)

    @Provides
    @Singleton
    fun itemMenuRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ItemMenuApi = retrofit.create(ItemMenuApi::class.java)

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
    fun stopApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): StopApi = retrofit.create(StopApi::class.java)

    @Provides
    @Singleton
    fun rActivityStopApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): RActivityStopApi = retrofit.create(RActivityStopApi::class.java)

    @Provides
    @Singleton
    fun rEquipActivityApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): REquipActivityApi = retrofit.create(REquipActivityApi::class.java)

    @Provides
    @Singleton
    fun rOSActivityApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ROSActivityApi = retrofit.create(ROSActivityApi::class.java)

    @Provides
    @Singleton
    fun turnApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): TurnApi = retrofit.create(TurnApi::class.java)

}